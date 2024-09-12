import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import ExecutionChain from '../helpers/ExecutionChain'
import ExercisesPage from '../helpers/ExercisesPage'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'
import ResultPage from '../helpers/ResultPage'
describe('template spec', () => {
  it('passes', () => {
    UrlControls.visitResults(3)
    ResultPage.shouldBeVisible()
    ResultPage.ResultPanel.ExecutionTimeShouldEqual(250)
    ResultPage.ResultPanel.SolutionBetterProcetShouldBE(66)
    ResultPage.ResultPanel.resulrRankShouldBe(5)
    ResultPage.Rating.clickRating(0)
    ResultPage.Rating.selectedRating("Very easy")
    ResultPage.Rating.clickRating(1)
    ResultPage.Rating.selectedRating("Easy")
    ResultPage.Rating.clickRating(2)
    ResultPage.Rating.selectedRating("Moderate")
    ResultPage.Rating.clickRating(3)
    ResultPage.Rating.selectedRating("Hard")
    ResultPage.Rating.clickRating(4)
    ResultPage.Rating.selectedRating("Very hard")
    ResultPage.Rating.clickRate();
    UrlControls.urlShouldBe("Solutions/3")
  })

})
