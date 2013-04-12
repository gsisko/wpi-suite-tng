function SelectPage(){
	setUp();

    $(document).ready(function () {
	
        var str = document.URL.split("=")[1];
        if (str == undefined)
            return;
        changePage(str);
    });
}

function SelectNextButton(){
    changePage(nextString);
}

function SelectPrevButton(){
    changePage(prevString);
}

/*
    Add the name of the function and the matching name of what you put in the url in this function
    set up the else if like below
*/
function changePage(str){
	if (str.toString() == "mainPanel")
		MainPanel();
    else if (str.toString() == "makeReqPage")
        MakeReqPage();

}


/*
    make a function matching this one. the .moveTabs area is where you put the header, all you have to do is change the text within the <a> tags
    the instruction text is where you will be adding the actual text. You can do any normal string functions, make helpers, add to a straing, even
    add images with the <img> tag (or <image> one of those) use escape sequences like normal strings an html tags as needed 
    try not to use <p> to keep me happy. Use <br /> for newline
*/

function MainPanel() {
    $(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">Basic Overview of the Main Panel</a>');
        $(".instructionTitle").html('The Main Panel');
		$(".instructionText").html(function(){
            return '<a>Hello World</a>'
        });
    });
}

function MakeReqPage() {
    $(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Create a Requirement</a>');
        $(".instructionText").html(function(){
            return '<a>Hello World</a>'
        });
    });
}



/*
    add your new page to the list below
    using the same helpArray.length position
    this isn't actually used right now,
    but may be implemented later so keep it up to date please.
*/
var helpArray = new Array();
helpArray[helpArray.length] = "makeReqPage";

var prevString = helpArray[helpArray.length - 1];
var nextString = helpArray[0];

function UpdateNextPrev(current){

    var pageID = helpArray.indexOf(current);
    var prevNum = -1;
    var nextNum = -1;

    if (pageID == -1)
        alert("This page doesnt actually seem to exist.");

    if (pageID == 0)
        prevNum = helpArray.length - 1;
    else
        prevNum = pageID--;

    if (pageID == helpArray.length - 1)
        nextNum = 0;

    prevString = helpArray[prevNum];
    nextString = helpArray[nextNum];
}