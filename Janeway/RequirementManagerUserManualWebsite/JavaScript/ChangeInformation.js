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
	if (str.toString() == "requirementOverview")
		RequirementOverview();
	else if (str.toString() == "makeReqPage")
        MakeReqPage();
	else if (str.toString() == "updateReqPage")
        UpdateReqPage();
        else if (str.toString() == "inlineUpdate")
        	InlineUpdate();
        
	else if (str.toString() == "addingANote")
        AddingANote();
	else if (str.toString() == "deleteReqPage")
        DeleteReqPage();
	else if (str.toString() == "addingUsers")
		AddingUsers();
	else if (str.toString() == "checkHistory")
		CheckHistory();
	else if (str.toString() == "addingATest")
		AddingATest();
	else if (str.toString() == "addingAnAttachment")
		AddingAnAttachment();
	
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
		
	else if (str.toString() == "makeChart")
		MakeChart();

}


/*
    make a function matching this one. the .moveTabs area is where you put the header, all you have to do is change the text within the <a> tags
    the instruction text is where you will be adding the actual text. You can do any normal string functions, make helpers, add to a straing, even
    add images with the <img> tag (or <image> one of those) use escape sequences like normal strings an html tags as needed 
    try not to use <p> to keep me happy. Use <br /> for newline
*/

function RequirementOverview(){
	$(document).ready(function (){
		$(".moveTabs").html('<a class="title">Overview of the Requirement Panel</a>');
		$(".instructionTitle").html('<a>The Requirement Manager Tab</a>');
		$(".instructionText").html(function (){
			var str = '<img width="750" src="images/mainPanel.png"/><br />';
			str += 'The above image shows the requirement manager in full. This section of the user manual will focus on the creation and management of requirements. Further sections will focus on the rest of our requirement manager, including Filters and Iterations.'
			return str;
		});
	});
}

function MakeReqPage() {
    $(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Create a Requirement</a>');
		$(".instructionTitle").html('<a>The Requirement Builder Panel for Creation</a>');
        $(".instructionText").html(function(){
        	var str = '<img width="500" src="images/topPanel.png"/><br />';
        	str += '<a>To begin the process of making a requirement simply <span style="color:#ED1C24">click the "Create Requirement" button</span> in the top left of the full display. The button is highlighted above</a><br />';
        	str += '<img width = "500" src="images/requirementBuilder.png" alt="The Requirement Builder Panel" /><br />';
        	str += '<a>The above image displays the requirement builder panel. The fields <span style="color:#ED1C24">name and description are required</span> and provide textual warnings when not properly entered. The <span style="color:#FF7F27">optional requirements are type, prority and relase number</span>. You can set the optional values either before or after creation of the requirement. Optional requirements will not prevent the creation of a requirement unless an invalid value is input, such as null. The fields: <b>status, estimate, actual effort, and iteration, are disabled</b> at the creation of the requirement.</a>';
        	str += '<br /><br /><a> The minimum that is required to successfully create a requirement is to give the requirement a name and description then click the "Save Changes" button at the top of the window.</a>';
            return str;
        });
    });
}

function UpdateReqPage(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Update a Requirement</a>');
		$(".instructionTitle").html('<a>The Requirement Builder Panel</a>');
        $(".instructionText").html(function(){
        	var str = '<img width="750" src="images/requirementListPanel.png"/><br />';
        	str += '<a>From the "List Requirements" tab, <span style="color:#ED1C24">double click on the requirement you wish to edit</span>. This will bring you to a screen identical to that of creating a requirement, though different areas are enabled or disabled.</a><br/>';
        	str += '<img width = "500" src="images/editRequirementPanel.png"/><br />';
        	str += '<a>As the above images shows, any <span style="color:#22B14C">fields previously entered are preserved</span>, in this case that is only the name and description. While other fields have changed from disabled to <span style="color:#FF7F27">optional</span>, those are: status, estimate, and actual Effort. <b>Iteration is still disabled</b> as iterations can only be set for requirements that have an estimate value set.</a><br/>';
        	str += '<img width="500" src = "images/editedRequirementPanel.png"/><br />';
        	str += '<a>When a value has changed, such as editing the description of the requirement, the box of said field will become yellowed to <span style="color:#d3c500"><b>indicate there is an unsaved change</b></span>. To <span style="color:#ED1C24">cancel unsaved changes click the "x" button</span> at the top of the current requirement tab. This will bring up a message box asking you if you want to discard your changes. Click "yes" to discard your changes and close the tab. Click "no" to return to the tab if you want to continue editing with your unsaved changes preserved. If you wish to save the changes, then <span style="color:#22B14C">click the "Save Changes" button</span> on the toolbar above</a>';
        	return str;
        });
    });
}

function InlineUpdate(){
	$(document).ready(function () {
		$(".moveTabs").html('<a class="title">How to Update a Requirement Inline</a>');
		$(".instructionTitle").html('<a>The Inline Requirement Editor</a>');
		$(".instructionText").html(function (){
			var str = '<img src="images/inlineEditButton.png" /><br />';
			str += '<a>To edit inline, begin by clicking the Enable Edit Mode button to have the default building screen change into an inline editing screen.</a><br /><br />';
			str += '<img src="images/editPanel.png" /><br/>';
			str += '<a>Once in the inline editing panel you can select the row you wish to edit, and edit it directly in the window. If you enter an impossible value the field will turn red. Allowed changes will turn yellow.</a>';
			
			return str;
		});
	});
}

function CheckHistory(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Check Requirement History</a>');
		$(".instructionTitle").html('<a>The Requirement History Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img src="images/requirementBuilderWithHistory.png" height="400"/><br/>';
			str += '<a>To view a requirements history of changes, simply <span style="color:#ED1C24">select the History tab</span> from the panel on the right of the requirement builder view. From this history tab you can view all changes made to this requirement since it\'s creation.</a>';
			return str;
        });
    });
}

function AddingANote() {
    $(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Add a Note to A Requirement</a>');
		$(".instructionTitle").html('<a>The Requirement Builder Panel With Notes</a>');
        $(".instructionText").html(function(){
        	var str = '<img width="750" src="images/requirementBuilderWithNotes.png"/><br />';
        	str += '<a>The panel for adding notes is displayed directly next to where requirements are edited. <span style="color:#22B14C">Click on the Notes tab to open the notes view</span>. Requirements yet to be created cannot have notes, therefore the notes panel is disabled before creation of requirements.</a><br />';
        	str += '<br /><a>To add a new note to the Notes panel, <span style="color:#ED1C24">type the desired note into the text field</span> marked with the arrow. When the note is composed as desired, <span style="color:#ED1C24">click the "Add Note" button</span> to add the note to the Panel.</a>';
        	str += '<br /><img width="750" src="images/addedNote.png"/><br />';
        	str += '<a>Upon clicking the "Add Note" button, your note will appear in the Notes panel with your <span style="color:#22B14C">name and the time</span> at which the note was added.</a>';
        	return str;
        });
    });
}

function AddingATest(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Add an Acceptance Test</a>');
		$(".instructionTitle").html('<a>The Acceptance Test Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img src="images/requirementBuilderWithTests.png" height="400"/><br/>';
			str += '<a>To add an acceptance test, you must first <span style="color:#22B14C">select the acceptance test tab</span> from the right side of the requirement builder panel.  Then you must enter a <span style="color:#ED1C24">required name and body</span> into the provided boxes. The name must be less then 100 characters, and both the name and body must be non-blank. After a name and descrption have been entered, you can <span style="color:#00A2E8">click the "Add Acceptance Test" button to add the test</span> to the requirement</a><br/><br/>';
			str += '<img src="images/enteredTest.png" height="100" /><br/>';
			str += '<a>The test will appear in the Panel above where you entered information, with a drop down menu to <span style="color:#ED1C24">select if the requirement passed or failed</span> the test.</a><br /><br />';
			str += '<a>Adding a test is disabled for requirements that have not been created or are deleted. Also, the status of acceptance test is disabled if the requirement is deleted.</a>';
			return str;
        });
    });
}

function AddingAnAttachment(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Add an Attachment</a>');
		$(".instructionTitle").html('<a>The Attachment Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img src="images/requirementBuilderWithAttachment.png" height="400"/><br/>';
			str += '<a>To add an attachment move to the right panel of the requirement builder panel and being by <span style="color:#22B14C">selecting the attachment tab</span>. From the attachment tab <span style="color:#ED1C24">click the "Add Attachment" button</span> to open a file browse screen to find and attach your file.</a>';
			return str;
        });
    });
}

function AddingUsers(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Assign a User to a Requirement</a>');
		$(".instructionTitle").html('<a>The User Assignment Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img src="images/Users.gif" height="400"/><br />';
			str += '<a>To add a user to a requirement you must start with an existing requirement. In the right hand panel, where you find history, notes and acceptance Tests, <span style="color:#ED1C24">select the tab that says user</span>. Within that tab you will find a list of users currently in the database. <span style="color:#ED1C24">Select the user or users you wish</span> to assign to the requirement then <span style="color:#ED1C24">click the "Add Users ->" button</span> to assign the user or users to the current requirement. The "Add Users ->" button will grey out as needed.<a/><br/><br/>';
			str += '<a>To remove a user from a requirement, follow the same instructions as for adding a user, but starting from the left of the user panel. That is: Select an assigned user you wish to unassign, click the "<- Remove Users" button, and the user will have been unassigned.</a>'
			return str;
        });
    });
}

function DeleteReqPage() {
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Delete a Requirement</a>');
		$(".instructionTitle").html('<a>The Requirement Builder Panel With Notes</a>');
        $(".instructionText").html(function(){
        	var str = '<a>To delete a requirement begin by opening up the requirement you wish to delete as if you were going to edit it.</a><br />';
        	str += '<img height = "500" src = "images/deleteRequirementPanel.png" /><br />';
        	str += '<a>To change a requirement to deleted <span style="color:#ED1C24">select "Deleted"</span> from the <span style="color:#22B14C">Status drop down menu</span> and save changes. </a> <br />'; //You cannot set the status to "Deleted" if the requirement is currently set to status "inProgress", and must first save the status to "Open" or "Completed" before being able to set the status to "Deleted".
        	str += '<br /><a>The deleted requirement will still be viewable from the List Requirements tab, but can be made invisible by activating a Filter where status != "Deleted". See the section on Filters for more information.</a>';
        	return str;
        });
    });
}

function IterationListPanel() {
    $(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">Overview of the Iteration List Panel</a>');
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
			str += '<ul><li>Name - The name of an iteration (cannot be blank or "backlog", cannot be more than 100 characters)</li>';
			str +='<li>Start Date - Date selected from the calender as the beginning of an iteration (cannot be after the end date)</li>';
			str +='<li>End Date - Date selected from the calendar as the end of an iteration (cannot be before the start date)</li></ul>';
			str +='<li>Dates cannot overlap other existing iteration dates</li></ul>'
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
	var str = '<a>To create a new iteration, click the "New Iteration" button in the Iteration List Panel. The Iteration Builder Panel then becomes active. Type in the desired non-blank, non-duplicated name of the iteration you would like to create in the name box.</a>';
	str += '<br /><br /><a>To set the start date, click on the small box to the right of the displayed start date. A calendar will appear where you can select the month with arrows next to the displayed month, and the day by clicking on any date. To set the end date, do the same for the displayed end date box. When selecting a date, some day boxed may be greyed out and inactive. This is because start dates need to occur before end dates, and iterations cannot have overlapping dates with other iterations</a>';
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
			var str = '<img height="300" src="images/filterlistPanel.png"/>';
			str += '<br /><a>The Filter List Panel displays all the filters that the user has created. Filters are saved to the user, not to the project. The columns display the filter type, operator, value, and whether it is active. If a filter is not active, it turns grey; otherwise, the active filter is white.</a>';
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
			str += '<br /><a>The Filter Builder Panel is used to create filters for the list of requirements. You are able to filter requirements by:</a>';
			str += '<ul><li>ID - Unique identification number for the requirement</li>';
			str += '<li>Name - Name of requirement</li>';
			str += '<li>User - Users assigned to the requirement</li>';
			str += '<li>Iteration - Iteration name of the requirement</li>';
			str += '<li>Status - Status of the requirement (New, In Progress, Open, Complete, Deleted)</li>';
			str += '<li>Priority - Priority of the requirement (No Priority, High, Medium, Low)</li>';
			str += '<li>Release Number - Release string or number of the requirement</li>';
			str += '<li>Estimate - Amount of work required for the requirement</li>';
			str += '<li>Actual Effort - Total effort that the requirement took</li></ul>';
			str += '<br /><a>The Operator dropdown box displays different operands to display by. The Value box is where you enter the information you want to filter by, if the value can be entered it must be non-blank. The Active dropdown box determines whether the filter is active or not. The "Save" button saves the filter and adds it to the Filter List.</a>';
			return str;
        });
    });
}

function CreateDeleteFilter(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Create and Delete Filters</a>');
		$(".instructionTitle").html('<a>The Filters</a>');
        $(".instructionText").html(function(){
			var str = '<a>To create a filter, first click the "New Filter" button located at the bottom of the Filter List Panel. Once clicked, the elements in the Filter Builder Panel will become active. You can create a filter with the type, operator, and value. Then hit the "Create" button. The filter will then appear on the left Filter List Panel.</a>';
			str += '<br /><br /><a>You can then edit the filter by double clicking on it. It opens in the Filter Builder Panel. You can edit the filter accordingly. If you did not mean to create or edit a requirement you can click the "Cancel" button on the bottom of the Filter List Panel. </a>';
			str += '<br /><br /><a>To delete a filter, simply double click the filter and click the "Delete" button. If you do not want to permanently delete the filter, you can double click the filter. The filter will open in the Filter Builder Panel, and you can change the filter from "Active" to "Inactive."</a>';
			return str;
        });
    });
}

function MakeChart(){
	$(document).ready(function () {
        //change where next and previous values point, do in a function
        $(".moveTabs").html('<a class="title">How to Make a Chart</a>');
		$(".instructionTitle").html('<a>The Chart Panel</a>');
        $(".instructionText").html(function(){
			var str = '<img src="images/displayChartsButton.png"/><br/>';
			str += '<a>To being viewing charts of your data, begin by click the <span style="color:#ED1C24">"Display Charts"</span> button from the top left of the main view.</a><br /><br />';
			str += '<img src="images/displayedChart.png" height="400" /><br />';
			str += '<a>From the chart view you may select either a <span style="color:#22B14C;">pie chart or bar chart</span> to display your data. ';
			str += 'You can also select the <span style="color:#00A2E8">Data you wish to disply</span>, such as requirement statuses, requirement iterations, number of requirements assigned to each user, and total estimate for each user. Lastly there is a menu for if you wish to <span style="color:#3F48CC">show applied filters or not.</span> Applying filters is disabled automatically if it filters out all requirements</a>'
			return str;
        });
    });
}
