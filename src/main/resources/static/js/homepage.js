//toggle all checkboxes
function toggleAll(){
    let checkboxes = document.getElementsByName('source');
    let button = document.getElementById('select-all');

    if(button.value === 'select'){
        for (let i in checkboxes){
            checkboxes[i].checked = 'FALSE';
        }
        button.value = 'deselect'
    }else{
        for (let i in checkboxes){
            checkboxes[i].checked = '';
        }
        button.value = 'select';
    }
}

//buttons animation
$(function () {
    $('.button-checkbox').each(function () {

        // Settings
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'fa fa-square-o'
                },
                off: {
                    icon: 'fa fa-check-square-o'
                }
            };

        // Event Handlers
        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });

        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (!isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }

        // Initialization
        function init() {

            updateDisplay();

            // Inject the icon if applicable
            if ($button.find('.state-icon').length === 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i>');
            }
        }
        init();
    });
});


var protein,fat,carbohydrate,energy;
var otherNuts = [];
var selectedFood;
var foodType;
var qt = 1;

function Nutrient(name, value, unit) {
    this.name = name;
    this.value = value;
    this.unit = unit;

    this.print = function() {
        return "Name:" + this.name + ", value:" + this.value + ", unit:" + this.unit;
    }
}

function displayEnergy() {
    if(energy.value !== null) {
        document.getElementById('energy-value').innerText = (energy.value*qt).toFixed(2) + ' kCal';
        document.getElementById('energy-container').setAttribute('style','display:table; margin:0 auto;');
    }
    else {
        document.getElementById('energy-value').innerText = "No information about energy";
        document.getElementById('energy-container').setAttribute('style','display:table; margin:0 auto;');
    }
}

function renderServingUnits() {
    const sSelect = $("#serving_unit");
    const servings = Object.keys(selectedFood.servingSizes);

    var options = "";
    sSelect.innerHTML = "";
    for (let i = 0; i < servings.length; i++) {
        options = options + "<option style='text-align: center;' value=\"" + servings[i] + "\">" + servings[i] + "</option>";
    }
    document.getElementById('serving_unit').innerHTML = options;
    document.getElementById("serving_size").value = Object.values(selectedFood.servingSizes)[0];

}

// Print chart
function printChart(carb,prot,fa) {
    $("#chartContainer").CanvasJSChart({
        data: [
            {
                type: "pie",
                radius: "50%",
                toolTipContent: "{label} <br/> {y}g",
                indexLabel: "{label} {y}g",
                dataPoints: [
                    { label: carb.name , y: (carb.value*qt).toFixed(2), legendText: carb.name + " in " + carb.unit},
                    { label: prot.name , y: (prot.value*qt).toFixed(2), legendText: prot.name + " in " + prot.unit},
                    { label: fa.name, y: (fa.value*qt).toFixed(2), legendText: fa.name + " in " + fa.unit}
                ]
            }
        ]
    });
}

function renderItemList(item,number) {
    var listRow = document.createElement('a');
    listRow.className = "list-group-item list-group-item-action d-inline-block btn-success";
    listRow.setAttribute("id","result-list-item-" + number);
    listRow.setAttribute("href","#result-list-item-" + number + "-details");
    listRow.setAttribute("role","tab");
    listRow.setAttribute("aria-controls","list-item-" + number);
    listRow.setAttribute("data-toggle","list");
    listRow.setAttribute("onclick","getFoodDetails(\"" + item.foodID + "\",\"" + item.dbName + "\",\"" + item.foodType + "\")");
    listRow.setAttribute("style","padding: 2px;");

    listRow.innerHTML = "<span>" + item.name + "</span>" +
        "<img class='imageClass d-inline-block mr-1' src=" + item.photo + " alt=\"" + item.name + "\"/>";


    document.getElementById('result-tab').appendChild(listRow);
}

// creates results list
function showResults() {
    var warningSpan = document.getElementById('search-warning-span');

    if (document.getElementById('searchInput').value.length >= 3) {
        warningSpan.setAttribute("style", "display:none;");

        $.ajax({
            url: "/api/food/autocomplete",
            method: "get",
            dataType: "json",
            data: {
                keyword: document.getElementById('searchInput').value
            },
            complete: function (res) {
                document.getElementById('result-tab').innerHTML = "";
                for(let i=0;i < res.responseJSON.length;i++)
                    renderItemList(res.responseJSON[i],i);
            }
        });
    }
    else
        warningSpan.setAttribute("style", "");
}


function getFoodDetails(id, dbName,fType) {
    otherNuts = []; //clear table
    foodType = fType;

    $.ajax({
        url: "/api/food/detailed",
        method: "get",
        dataType: "json",
        data: {
            foodID: id,
            dbName: dbName
        },
        complete: function (res) {
            selectedFood = res.responseJSON;

            for(let i=0;i<selectedFood.nutrients.length;i++) {
                let nw = selectedFood.nutrients[i];

                if (nw.nutrient.name.toLowerCase().includes("protein"))
                    protein = new Nutrient(nw.nutrient.name, nw.quantity, nw.nutrient.unit);
                if (nw.nutrient.name.toLowerCase().includes("fat"))
                    fat = new Nutrient(nw.nutrient.name, nw.quantity, nw.nutrient.unit);
                if (nw.nutrient.name.toLowerCase().includes("carbohydrate"))
                    carbohydrate = new Nutrient(nw.nutrient.name, nw.quantity, nw.nutrient.unit);
                if (nw.nutrient.name.toLowerCase().includes("energy"))
                    energy = new Nutrient(nw.nutrient.name, nw.quantity, nw.nutrient.unit);
                else
                {
                    var nutr = new Nutrient(nw.nutrient.name, nw.quantity, nw.nutrient.unit);
                    otherNuts.push(nutr);
                }
            }

            document.getElementById('result-tab-content').innerHTML ="";//clear results tab
            // print chart and compute statistics if food contains required information
            if (carbohydrate !== null && protein !== null && fat !== null) {
                printChart(selectedFood.name, carbohydrate, protein, fat);

                var nutrSum = fat.value + protein.value + carbohydrate.value;
                var fPerc = fat.value / nutrSum;
                var pPerc = fat.value / nutrSum;
                var cPerc = fat.value / nutrSum;
            }
            else
                document.getElementById("chartContainer").innerHTML = "Could not print chart.";

            // render empty nutrients table
            var detailsDiv = document.createElement('div');
            detailsDiv.className = "tab-pane show active";
            detailsDiv.setAttribute("id", "result-list-item-1-details");
            detailsDiv.setAttribute("role", "tabpanel");
            detailsDiv.setAttribute("aria-labelledby", "result-list-item-1");
            detailsDiv.innerHTML = "        <table>\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th>Name</th>\n" +
                "                    <th>unit</th>\n" +
                "                    <th>value</th>\n" +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody id='nutrients-table-body'>\n" +
                "            </tbody>\n" +
                "        </table>";
            document.getElementById('result-tab-content').appendChild(detailsDiv);

            //fill table with data
            var rows = "";
            for (let i = 0; i < otherNuts.length; i++) {
                rows = rows + "<tr>\n" +
                    "<td style='text-align: left;'>" + otherNuts[i].name + "</td>" +
                    "<td>" + otherNuts[i].unit + "</td>" +
                    "<td>" + otherNuts[i].value + "</td>" +
                    "\n</tr>";
            }
            document.getElementById('nutrients-table-body').innerHTML = rows;

            displayEnergy();
            renderServingUnits();
            document.getElementById('serving_size').value = selectedFood.servingSizes[0];
     }});


}

function updateDetails() {
    const quantity = $("#serving_size").val();
    const unit = $("#serving_unit").val();

    if(unit === "G")
        qt = quantity/selectedFood.servingSizes["G"];
    else {
        if(selectedFood.servingSizes["G"] != null )
            qt=quantity*selectedFood.servingSizes[unit]/selectedFood.servingSizes["G"];
        else
            qt = quantity*selectedFood.servingSizes[unit];
    }

    //update table with data
    var rows="";
    for(let i=0;i<otherNuts.length;i++) {
        rows = rows +  "<tr>\n" +
            "<td>" + otherNuts[i].name + "</td>" +
            "<td>" + otherNuts[i].unit + "</td>" +
            "<td>" + (otherNuts[i].value*qt).toFixed(2)+ "</td>" +
            "\n</tr>";
    }
    document.getElementById('nutrients-table-body').innerHTML=rows;
    displayEnergy();

    if(carbohydrate !== null && protein !== null && fat !== null)
        printChart( carbohydrate,protein,fat);
}


/*
=======================================
Food diary logger scripts
=======================================
 */
$('#diary-time').clockpicker({
    autoclose: true
});

function setTimeToNow() {
    var currentTime =new Date().toLocaleTimeString().substring(0,5);
    document.getElementById('diary-time').value =currentTime;
    currentTime = currentTime.substring(0,2);

    // clear selection
    document.getElementById('supper').className = "btn btn-success p-1";
    document.getElementById('breakfast').className = "btn btn-success p-1";
    document.getElementById('lunch').className = "btn btn-success p-1";
    document.getElementById('dinner').className = "btn btn-success p-1";


    if(currentTime >= 19 || currentTime <= 4)
        document.getElementById('supper').className = "btn btn-success p-1 active";
    else if(currentTime > 4 && currentTime < 11 )
        document.getElementById('breakfast').className = "btn btn-success p-1 active";
    else if(currentTime >= 11 && currentTime <= 14 )
        document.getElementById('lunch').className = "btn btn-success p-1 active";
    else
        document.getElementById('dinner').className = "btn btn-success p-1 active";
}



$(function () {
    var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});


function logFood() {
    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');
    var food = {};
    var loggedInfo = $('#logged-success');

    food.serving_unit = document.getElementById("serving_unit")
        .options[document.getElementById("serving_unit").selectedIndex].value;

    var idKey = Object.keys(selectedFood.foreignIDs)[0];
    food.entityDbName = idKey;
    food.entityID = selectedFood.foreignIDs[idKey];

    food.serving_size = document.getElementById("serving_size").value;
    food.time = document.getElementById("diary-time").value;
    food.date = document.getElementById("diary-date").value;
    food.foodType = foodType;

    const mealRadios = document.getElementById('diary-meal');
    let mealLabel = mealRadios.getElementsByClassName('active')[0];
    food.meal = mealLabel.firstElementChild.value;


    $(document).ajaxSend(function (e, xhr, options) { //include CSRF tokens to request
        xhr.setRequestHeader(header, token);
    });

    $.ajax({
        url: "/api/diets/logger",
        type: "post",
        data: JSON.stringify(food),
        contentType: "application/json",
        error: function () {
            loggedInfo.innerText = "Error while sending food to log";
            loggedInfo.fadeIn('slow', function () {
                loggedInfo.delay(3000).fadeOut();
            });
        },
        complete: function () {
            $(function () {
                loggedInfo.innerText = "Successfully added to diary";
                loggedInfo.fadeIn('slow', function () {
                    loggedInfo.delay(3000).fadeOut();
                });
            })
        }
    });
}