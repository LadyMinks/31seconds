package nl.ladyminks.thirtyoneseconds.logic.screens.editteam;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.factories.NameFactory;
import nl.ladyminks.thirtyoneseconds.logic.repositories.CategoryRepository;
import nl.ladyminks.thirtyoneseconds.logic.repositories.TeamsRepository;

/**
 * @author Minka Firth
 */
public class EditTeamPresenter {

    private final nl.ladyminks.thirtyoneseconds.logic.screens.editteam.EditTeamView editTeamView;
    private final NameFactory nameFactory;
    private final CategoryRepository categoryRepository;
    private final TeamsRepository teamsRepository;
    private final Team team;

    public EditTeamPresenter(EditTeamView editTeamView, NameFactory nameFactory, CategoryRepository categoryRepository, TeamsRepository teamsRepository, String teamID) {
        this.editTeamView = editTeamView;
        this.nameFactory = nameFactory;
        this.categoryRepository = categoryRepository;
        this.teamsRepository = teamsRepository;

        if (teamID == null) {
            //  if teamID = null, means that a new team will be made.
            this.team = new Team(nameFactory.generateName(), categoryRepository.loadCategories(), UUID.randomUUID().toString());
        } else {
            // a team will be found and then loaded by its teamID.
            this.team = teamsRepository.loadTeam(teamID);
        }
    }

    /**
     * Is called when the Presenter is created. Startup code for Presenter.
     */
    public void onCreate() {
        editTeamView.displayName(team.getName());
        editTeamView.displayCategories(categoryRepository.loadCategories(),
                new HashSet<>(team.getPlayingCategories()));
    }

    /**
     * Generates a new random Team Name.
     */
    public void generateAgain() {
        editTeamView.displayName(nameFactory.generateName());
    }

    /**
     * Saves the team with its Name, List of playing Categories and TeamID.
     *
     * @param name
     *         the name of the Team.
     * @param playingCategories
     *         the List of Categories this Team will be playing with.
     */
    public void saveTeam(String name, List<Category> playingCategories) {
        teamsRepository.storeTeam(new Team(name, playingCategories, team.getTeamID()));
    }

}


