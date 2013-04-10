function setUp()
{
    $(document).ready(function () {

        var startHeight = $(window).height();
        var startWidth = $(window).width();

        var onePerHeightPadding = startHeight * .01;
        var onePerWidthPadding = startWidth * .01;

        var navPaneWidth = startWidth / 3 - 4 * onePerWidthPadding;
        var navPaneHeight = startHeight - 6 * onePerHeightPadding;
        var infoPaneWidth = startWidth * 2 / 3 - 4 * onePerWidthPadding;
        var infoHeight = startHeight - 4 * onePerHeightPadding;
        var infoPaneHeight = startHeight - 8 * onePerHeightPadding - $(".changeInstructionTab").height();

        $(".navigationPanel").height(navPaneHeight).width(navPaneWidth);
        $(".navigationPanel").css("margin-top", onePerHeightPadding).css("margin-botom", onePerHeightPadding);
        $(".navigationPanel").css("padding-top", onePerHeightPadding).css("padding-botom", onePerHeightPadding);
        $(".navigationPanel").css("margin-left", onePerWidthPadding)//.css("margin-right", onePerWidthPadding);

        $(".informationPanel").height(infoHeight);
        $(".informationPanel").css("margin-top", onePerHeightPadding).css("margin-botom", onePerHeightPadding);
        $(".informationPanel")/*.css("margin-left", onePerWidthPadding)*/.css("margin-right", onePerWidthPadding);

        $(".instructionPanel").height(infoPaneHeight).width(infoPaneWidth);
        $(".instructionPanel").css("margin-top", onePerHeightPadding).css("margin-botom", onePerHeightPadding);
        $(".instructionPanel").css("padding-left", onePerWidthPadding).css("padding-right", onePerWidthPadding);

        $(".changeInstructionTab").height($(".switchbutton").height()).width(infoPaneWidth);
        $(".changeInstructionTab").css("padding-top", onePerHeightPadding).css("padding-bottom", onePerHeightPadding);
        $(".changeInstructionTab").css("padding-left", onePerWidthPadding).css("padding-right", onePerWidthPadding);
        //$(".changeInstructionTab").css("margin-top", onePerHeightPadding);
        //$(".changeInstructionTab").css("margin-right", onePerWidthPadding);

        $(".NavSelectList").height(navPaneHeight - 3 * $(".navTitle").height());

        $(".textPanel").css("margin-top", onePerHeightPadding);
        $(".textPanel").css("margin-left", onePerWidthPadding).css("margin-right", onePerWidthPadding);

        $(".instructionText").height(infoPaneHeight - 3 * $(".instructionTitle").height());

        SelectPage();
    });
}