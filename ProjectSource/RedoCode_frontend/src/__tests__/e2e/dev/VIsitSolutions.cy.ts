import UrlControls from "../helpers/UrlControls";
import SolutionsList from "../helpers/SolutionsList"
import LoadingPanel from "../helpers/LoadingPanel"
describe('template spec', () => {
  it('passes', () => {
    const comment="Comment"

 UrlControls.visitSolutions(3);
 LoadingPanel.shouldBeVisble();
 LoadingPanel.shouldNotExist();
 SolutionsList.shouldTitleBe("Exercise Title")
 SolutionsList.shouldDescBe("Descritpion of exercise")
SolutionsList.solutionListClick(0);
SolutionsList.solutionItemListShouldBeSelected(0);
SolutionsList.shouldUsernameCommentBe(0,"example user");
SolutionsList.shouldContentCommentBe(0,"Greate exercise");
SolutionsList.postComment(comment)
// SolutionsList.shouldUsernameCommentBe(0,"example user");
SolutionsList.shouldContentCommentBe(0,comment);
SolutionsList.shouldContentCommentBe(1,"Greate exercise");
SolutionsList.solutionListClick(2);
SolutionsList.solutionItemListShouldBeSelected(2);
})
})