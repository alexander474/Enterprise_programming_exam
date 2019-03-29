package no.ab.moc.frontend.controller;

import no.ab.moc.entity.Category;
import no.ab.moc.entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import no.ab.moc.service.CategoryService;
import no.ab.moc.service.MatchStatsService;
import no.ab.moc.service.QuizService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by arcuri82 on 06-Dec-17.
 */
@Named
@SessionScoped
public class MatchController implements Serializable {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private MatchStatsService statsService;

    @Autowired
    private UserInfoController infoController;

    private final int NUMBER_QUIZZES = 5;

    private boolean gameIsOn = false;
    private Long selectedCategoryId;
    private int counter;
    private List<Quiz> questions;

    public boolean isMatchOn() {
        return gameIsOn;
    }

    public String newMatch() {

        String username = infoController.getUserName();

        if (gameIsOn) {
            statsService.reportDefeat(username);
        }
        gameIsOn = true;

        selectedCategoryId = null;

        return "/ui/match.jsf&faces-redirect=true";
    }

    public boolean isCategorySelected() {
        return selectedCategoryId != null;
    }

    public void selectCategory(long id) {
        selectedCategoryId = id;
        counter = 0;
        questions = quizService.getRandomQuizzes(NUMBER_QUIZZES, selectedCategoryId);
    }

    public Quiz getCurrentQuiz() {
        return questions.get(counter);
    }

    public int getIncreasedCounter() {
        return counter + 1;
    }

    public int getNumberOfQuizzes() {
        return NUMBER_QUIZZES;
    }

    public String answerQuiz(int index) {

        String username = infoController.getUserName();

        Quiz quiz = getCurrentQuiz();
        if (index == quiz.getIndexOfCorrectAnswer()) {
            counter++;
            if (counter == NUMBER_QUIZZES) {
                gameIsOn = false;
                statsService.reportVictory(username);
                return "result.jsf?victory=true&faces-redirect=true";
            }
        } else {
            gameIsOn = false;
            statsService.reportDefeat(username);
            return "result.jsf?defeat=true&faces-redirect=true";
        }

        return null;
    }

    public List<Category> getCategories() {

        return categoryService.getAllCategories(false);
    }
}
