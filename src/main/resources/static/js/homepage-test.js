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

// test data as result of searching
let input = "[{\"foodID\":\"1\",\"name\":\"good chips\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"COMMON\"},{\"foodID\":\"2\",\"name\":\"Pancake\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"COMMON\"},{\"foodID\":\"3\",\"name\":\"Pineapple\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"COMMON\"},{\"foodID\":\"4\",\"name\":\"Mechanical orange\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"COMMON\"},{\"foodID\":\"5\",\"name\":\"Cucumber\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"COMMON\"},{\"foodID\":\"6\",\"name\":\"Pork\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"COMMON\"}]";
let foodArray = JSON.parse(input);


function renderItemList(item,number) {
    var listRow = document.createElement('a');
    listRow.className = "list-group-item list-group-item-action d-inline-block";
    listRow.setAttribute("id","result-list-item-" + number);
    listRow.setAttribute("href","#result-list-item-" + number + "-details");
    listRow.setAttribute("role","tab");
    listRow.setAttribute("aria-controls","list-item-" + number);
    listRow.setAttribute("data-toggle","list");
    listRow.setAttribute("onclick","getFoodDetails(" + item.foodID + ",\"" + item.dbName + "\")");
    listRow.innerHTML = "<span class='d-inline-block'>" + item.name + "</span>" +
        "<img class='imageClass d-inline-block' src=../../static/" + item.photo + " alt=\"" + item.name + "\"/>";

    document.getElementById('result-tab').appendChild(listRow);
}

// creates results list
function showResults() {
    document.getElementById('result-tab').innerHTML ="";
    let i = 0;
    foodArray.forEach(function(element) {
        i++;
        renderItemList(element,i);
    });
}

function getFoodDetails(id, dbName) {
    document.getElementById('result-tab-content').innerHTML ="";

    var detailsDiv = document.createElement('div');
    detailsDiv.className = "tab-pane show active";
    detailsDiv.setAttribute("id","result-list-item-1-details");
    detailsDiv.setAttribute("role","tabpanel");
    detailsDiv.setAttribute("aria-labelledby","result-list-item-1");
    detailsDiv.innerHTML = "<div> "  + "Selected food details:" + "ID:" + id + ", dbName:" + dbName +
        "</div>";
    document.getElementById('result-tab-content').appendChild(detailsDiv);
}


