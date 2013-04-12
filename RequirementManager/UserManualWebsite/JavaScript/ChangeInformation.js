function SelectPage(){
	setUp();

    $(document).ready(function () {
	
        var str = document.URL.split("page=")[1];
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
		
	else if (str.toString() == "filterListPanel")
		FilterListPanel();
	else if (str.toString() == "filterBuilderPanel")
		FilterBuilderPanel();
	else if (str.toString() == "createDeleteFilter")
		CreateDeleteFilter();
		
	else if (str.toString() == "iterationListPanel")
		IterationListPanel();
	else if (str.toString() == "iterationBuilderPanel")
		IterationBuilderPanel();
	else if (str.toString() == "createDeleteIteration")
		CreateDeleteIteration();

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
        $(".instructionTitle").html('<a>The Main Panel</a>');
		$(".instructionText").html(function(){
            return '<a>Hello World</a>'
        });
    });
}

function MakeReqPage() {
    $(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Create a Requirement</a>');
		$(".instructionTitle").html('<a>The Main Panel</a>');
        $(".instructionText").html(function(){
            return '<a>Hello World</a>'
        });
    });
}


function IterationListPanel() {
    $(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">OverView of the Iteration List Panel</a>');
		$(".instructionTitle").html('<a>The Iteration List Panel</a>');
        $(".instructionText").html(function(){
            var str = '<img height="400" src="images/iterationListPanel.png"/>';
			str += '<br /><a>The Iteration List Panel is a tab that displays the name, start date, and end date of all iterations. To interact with the list panel, double click on an iteration to edit it. "New Iteration" and "Delete" buttons at the bottom of the panel allow iterations to be created and deleted. </a>'
			return str;
		});
    });
}

function IterationBuilderPanel(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">Overview of the Iteration Builder Panel</a>');
		$(".instructionTitle").html('<a>The Iteration Builder Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img width="750" src="images/iterationBuilderPanel.png"/>';
			str += '<br /><a>The Iteration Builder Panel allows you to create and update iterations. Iterations have the following attributes:</a>';
			str += '<ul><li>Name - The name of an iteration</li>';
			str +='<li>Start Date - Date selected from the calender as the beginning of an iteration (cannot be after the end date)</li>';
			str +='<li>End Date - Date selected from the calendar as the end of an iteration (cannot be before the start date)</li></ul>'
			return str;
		});
    });
}

function CreateDeleteIteration(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Create and Delete Iterations</a>');
		$(".instructionTitle").html('<a>The Iteration</a>');
        $(".instructionText").html(function(){
			var str = '<a>To create a new iteration, click the "New Iteration" button in the Iteration List Panel. The Iteration Builder Panel then becomes active. Type in the desired name of the iteration you would like to create in the name box.</a>';
            str += '<br /><br /><a>To set the start date, click on the small box to the right of the displayed start date. A calendar will appear where you can select the month with arrows next to the displayed month, and the day by clicking on any date. To set the end date, do the same for the displayed end date box. When selecting a date, some day boxed may be greyed out and inactive. This is because start dates need to occur before end dates, and iterations cannot have overlapping dates.</a>';
			str += '<br /><br /><a>To delete an iteration, select it from the Iteration List Panel, and click the "Delete" button at the bottom of the panel.</a>';
			return str;
        });
    });
}

function FilterListPanel(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">Overview of the Filter List Panel</a>');
		$(".instructionTitle").html('<a>The Filter List Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img width="500" src="images/filterBuilderPanel.png"/>';
			str += '<br /><a>The Filter Builder Panel is used to create filters for the list of requirements. You are able to filter requirements by:</a>';
			str += '<ul><li>ID - Unique identification number for the requirement</li>';
			str += '<li>Name - Name of requirement</li>';
			str += '<li>Iteration - Iteration name of the requirement</li>';
			str += '<li>Status - Status of the requirement (New, In Progress, Open, Complete, Deleted)</li>';
			str += '<li>Priority - Priority of the requirement (No Priority, High, Medium, Low)</li>';
			str += '<li>Release Number - Release string or number of the requirement</li>';
			str += '<li>Estimate - Amount of work required for the requirement</li>';
			str += '<li>Actual Effort - Total effort that the requirement took</li></ul>';
			str += '<br /><a>The Operator dropdown box displays different operands to display by. The Value box is where you enter the information you want to filter by. The Active dropdown box determines whether the filter is active or not. The "Save" button saves the filter and adds it to the Filter List.</a>';
			return str;
        });
    });
}

function FilterBuilderPanel(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">Overview of Filter Builder Panel</a>');
		$(".instructionTitle").html('<a>The Filter Builder Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img width="500" src="images/filterBuilderPanel.png" />';
			str += '<br /><a>The Filter List Panel displays all the filters that the user has created. Filters are saved to the user, not to the project. The columns display the filter type, operator, value, and whether it is active. If a filter is not active, it turns grey; otherwise, the active filter is white.</a>';
			return str;
        });
    });
}

function CreateDeleteFilter(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Create and Delete Iterations</a>');
		$(".instructionTitle").html('<a>The Iteration</a>');
        $(".instructionText").html(function(){
			var str = '<a>To create a filter, first click the "New Filter" button located at the bottom of the Filter List Panel. Once clicked, the elements in the Filter Builder Panel will become active. You can create a filter with the type, operator, and value. Then hit the "Create" button. The filter will then appear on the left Filter List Panel.</a>';
			str += '<br /><br /><a>You can then edit the filter by double clicking on it. It opens in the Filter Builder Panel. You can edit the filter accordingly. If you did not mean to create or edit a requirement you can click the "Cancel" button on the bottom of the Filter List Panel. </a>';
			str += '<br /><br /><a>To delete a filter, simply double click the filter and click the "Delete" button. If you do not want to permanently delete the filter, you can double click the filter. The filter will open in the Filter Builder Panel, and you can change the filter from "Active" to "Inactive."</a>';
			return str;
        });
    });
}

/*
    add your new page to the list below
    using the same helpArray.length position
    this isn't actually used right now,
    but may be implemented later so keep it up to date please.

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
}*/