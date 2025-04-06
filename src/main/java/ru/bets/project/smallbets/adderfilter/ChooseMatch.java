package ru.bets.project.smallbets.adderfilter;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.fonmatch.FonMatch;
import ru.bets.project.smallbets.fonmatch.FonTournament;

import java.util.ArrayList;
import java.util.List;

public class ChooseMatch {
    SharedState sharedState;

    public ChooseMatch(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    public void chooseMatch() {
        List<FilterTournament> listNameTournamentForFilter = new ArrayList<>();
        int indexListNameTournamentForFilter = -1;

        int tempIndex = -1; //нужен, чтобы турнир добавился лишь 1 раз в список турниров для фильтра
        boolean firstAdding;
        for(FonTournament fonTournament : sharedState.getListTournament())
        {
            if (fonTournament.isHaveSmall()) {
                firstAdding = true;
                for (FonMatch fonMatch : fonTournament.getMatches()) {
                    if (fonMatch.getYellowCard() != null) {
                        if (firstAdding) {
                            tempIndex++;
                            firstAdding = false;
                        }
                        if (listNameTournamentForFilter.size() < (tempIndex + 1)) {
                            listNameTournamentForFilter.add(new FilterTournament(fonTournament.getName()));
                            indexListNameTournamentForFilter++;
                        }
                        listNameTournamentForFilter.get(indexListNameTournamentForFilter).getListMatch().add(fonMatch.getName());
                    }
                }
            }
        }
       /*

       вернуть списки на представление или в телеграмм

        */

    }

    private class FilterTournament {
        private String name;
        private List<String> listMatch;

        public FilterTournament(String name) {
            this.name = name;
            listMatch = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getListMatch() {
            return listMatch;
        }

    }
}
