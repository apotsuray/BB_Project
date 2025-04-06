package ru.bets.project.smallbets;

import ru.bets.project.smallbets.fonmatch.FonTournament;
import ru.bets.project.telegram.Telegram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedState {
    private final Lock lock = new ReentrantLock();
    private final Condition parserThreadFinished = lock.newCondition();
    private final Condition algorithmThreadsFinished = lock.newCondition();
    private final Condition workWithInfoThreadsFinished = lock.newCondition();
    private boolean isParserThreadRunning = true; // Первый поток активен
    private final boolean[] threadParticipated = new boolean[16]; // Флаги участия потоков
    private int finishedThreadsCount = 0; // Счётчик завершившихся потоков
    private int activeWorkWithInfoThreads = 0;
    private List<FonTournament> listTournament;
    private final Telegram telegram;

    public SharedState(Telegram telegram) {
        this.telegram = telegram;
    }

    // Методы для первого потока
    public void waitForAlgorithmThreads() throws InterruptedException {
        lock.lock();
        try {
            while (!isParserThreadRunning) {
                algorithmThreadsFinished.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void signalAlgorithmThreads() {
        lock.lock();
        try {
            isParserThreadRunning = false;
            resetThreadParticipation();
            parserThreadFinished.signalAll();
        } finally {
            lock.unlock();
        }
    }
    public void incrementActiveWorkWithInfoThreads() {
        lock.lock();
        try {
            activeWorkWithInfoThreads++;
        } finally {
            lock.unlock();
        }
    }
    public void decrementActiveWorkWithInfoThreads() {
        lock.lock();
        try {
            activeWorkWithInfoThreads--;
            if (activeWorkWithInfoThreads == 0) {
                workWithInfoThreadsFinished.signal();
            }
        } finally {
            lock.unlock();
        }
    }
    public void waitForWorkWithInfoThreads() throws InterruptedException {
        lock.lock();
        try {
            while (activeWorkWithInfoThreads > 0) {
                workWithInfoThreadsFinished.await();
            }
        } finally {
            lock.unlock();
        }
    }
    // Методы для остальных потоков
    public void waitForParserThread(int threadIndex) throws InterruptedException {
        lock.lock();
        try {
            while (isParserThreadRunning || threadParticipated[threadIndex]) {
                parserThreadFinished.await();
            }
            threadParticipated[threadIndex] = true;
        } finally {
            lock.unlock();
        }
    }

    public void signalParserThread() {
        lock.lock();
        try {
            if (++finishedThreadsCount == 16) {
                isParserThreadRunning = true;
                finishedThreadsCount = 0;
                algorithmThreadsFinished.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    // Сброс флагов участия
    private void resetThreadParticipation() {
        Arrays.fill(threadParticipated, false);
    }
    public List<FonTournament> getListTournament() {
        return listTournament;
    }
    public void setListTournament() {
        listTournament = new ArrayList<>();
    }

    public Telegram getTelegram() {
        return telegram;
    }
}
