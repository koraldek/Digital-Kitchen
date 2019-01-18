//toggle all checkboxes
function toggleAll(){
    let checkboxes = document.getElementsByName('food-source');
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




// search result for keyword "turtle"
const searchResult = "[{\"foodID\":\"53304272044079d310ba24ed\",\"name\":\"Turtles\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/536101ecce96295343205990.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"c640556684a27af62bfc2504\",\"name\":\"Turtle\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"BRANDED\"},{\"foodID\":\"576d06bf6ad199aa29530da3\",\"name\":\"Caramel Nut Clusters, Double Chocolate\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/576d8d3c3eccb7620dabcb44.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"58aa95a33113a43a09238732\",\"name\":\"Caramel Nut Clusters\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/58abe79468b41fad1f8be486.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"586df15be4a3466d0b893541\",\"name\":\"Classic Recipe Chocolate And Pecan Clusters Candy\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/586f41ef4bb78e4d1d2882c4.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"58411c9604a44c61494da235\",\"name\":\"Caramel Nut Clusters, Sea Salt\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/58426efcae373d7c75fa4fbd.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"56b2e170bd8ccc4467bbfb19\",\"name\":\"Chocolate Bar\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/56b2e2164f8cdaef08864804.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"581ed5383b44ccf960af9b21\",\"name\":\"Original, Pecans, Chocolate, Caramel\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/581ed53c3b44ccf960af9b22.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"5b8a3c5a87ea14a24ff8563c\",\"name\":\"Cheesecake Turtle\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/5b8a3c5ebbf32eaa430572f8.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"56880641a1b8c86412aa5557\",\"name\":\"Dark Almond Chocolate\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/568806cf2952f850022f929b.jpeg\",\"origin\":\"BRANDED\"},{\"foodID\":\"ddee6761f35e01abac7edcfa\",\"name\":\"Turtle Sundae\",\"dbName\":\"Nutritionix\",\"photo\":\"/img/default_food_image.png\",\"origin\":\"BRANDED\"},{\"foodID\":\"52ae479aaf5a0bb91c015136\",\"name\":\"Cheesecake\",\"dbName\":\"Nutritionix\",\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/536bbbb14a749f8f03d612f7.jpeg\",\"origin\":\"BRANDED\"}]"
const searchResultArray = JSON.parse(searchResult);
// Detailed information about food on list
const fd1= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0218},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":5.1515},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":0.9091},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.2121},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1212},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.6061},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0606},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.4545}],\"foreignIDs\":{\"Nutritionix\":\"53304272044079d310ba24ed\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/536101ecce96295343205990.jpeg\",\"names\":{\"en\":\"Turtles\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"pieces\":1,\"grams\":33},\"intolerances\":[],\"name\":\"Turtles\"}");
const fd2= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":127.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":13.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":155.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":1260.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":3.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":31.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":166.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":420.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":62.0}],\"foreignIDs\":{\"Nutritionix\":\"c640556684a27af62bfc2504\"},\"photo\":\"/img/default_food_image.png\",\"names\":{\"en\":\"Turtle\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"grams\":0,\"each\":1},\"intolerances\":[],\"name\":\"Turtle\"}");
const fd3= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":0.1515},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0218},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":5.1515},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":0.9091},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.5758},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.2121},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1364},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.3333},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0606},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.4545}],\"foreignIDs\":{\"Nutritionix\":\"576d06bf6ad199aa29530da3\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/576d8d3c3eccb7620dabcb44.jpeg\",\"names\":{\"en\":\"Caramel Nut Clusters, Double Chocolate\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"pieces\":1,\"grams\":33},\"intolerances\":[],\"name\":\"Caramel Nut Clusters, Double Chocolate\"}");
const fd4= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":0.1515},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":1.5152},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0109},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":5.1515},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.5758},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.3333},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.2121},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1212},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0606}],\"foreignIDs\":{\"Nutritionix\":\"58aa95a33113a43a09238732\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/58abe79468b41fad1f8be486.jpeg\",\"names\":{\"en\":\"Caramel Nut Clusters\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"pieces\":1,\"grams\":33},\"intolerances\":[],\"name\":\"Caramel Nut Clusters\"}");
const fd5= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.4706},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1176},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.2941},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.5882},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.1765},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0588},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":0.8824},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":5.2941},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0212}],\"foreignIDs\":{\"Nutritionix\":\"586df15be4a3466d0b893541\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/586f41ef4bb78e4d1d2882c4.jpeg\",\"names\":{\"en\":\"Classic Recipe Chocolate And Pecan Clusters Candy\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"piece\":1,\"grams\":17},\"intolerances\":[],\"name\":\"Classic Recipe Chocolate And Pecan Clusters Candy\"}");
const fd6= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0436},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.5152},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.2121},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1212},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":3.9394},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":4.8485},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0606},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.4848}],\"foreignIDs\":{\"Nutritionix\":\"58411c9604a44c61494da235\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/58426efcae373d7c75fa4fbd.jpeg\",\"names\":{\"en\":\"Caramel Nut Clusters, Sea Salt\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"pieces\":1,\"grams\":33},\"intolerances\":[],\"name\":\"Caramel Nut Clusters, Sea Salt\"}");
const fd7= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.194},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0299},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":0.9701},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":605,\"usda_tag\":\"FATRN\",\"name\":\"Fatty acids, total trans\",\"unit\":\"gram\"},\"quantity\":0.0015},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":0.0746},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":5.0746},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0597},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":401,\"usda_tag\":\"VITC\",\"name\":\"Vitamin C, total ascorbic acid\",\"unit\":\"mili gram\"},\"quantity\":0.0179},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.2836},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.5821},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.4627},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0269},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1045}],\"foreignIDs\":{\"Nutritionix\":\"56b2e170bd8ccc4467bbfb19\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/56b2e2164f8cdaef08864804.jpeg\",\"names\":{\"en\":\"Chocolate Bar\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"pieces\":1,\"grams\":67},\"intolerances\":[],\"name\":\"Chocolate Bar\"}");
const fd8= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":0.1515},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":1.2121},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0109},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":5.1515},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.5758},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.2121},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1212},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0606},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.4848}],\"foreignIDs\":{\"Nutritionix\":\"581ed5383b44ccf960af9b21\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/581ed53c3b44ccf960af9b22.jpeg\",\"names\":{\"en\":\"Original, Pecans, Chocolate, Caramel\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"pieces\":1,\"grams\":33},\"intolerances\":[],\"name\":\"Original, Pecans, Chocolate, Caramel\"}");
const fd9= JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.2706},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.4},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":318,\"usda_tag\":\"VITA_IU\",\"name\":\"Vitamin A, IU\",\"unit\":\"international Unit\"},\"quantity\":5.8824},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0118},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":3.7647},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0169},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1059},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0471},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.2118},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":0.7059},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":0.6471},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":2.1176}],\"foreignIDs\":{\"Nutritionix\":\"5b8a3c5a87ea14a24ff8563c\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/5b8a3c5ebbf32eaa430572f8.jpeg\",\"names\":{\"en\":\"Cheesecake Turtle\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"slice\":1,\"grams\":85},\"intolerances\":[],\"name\":\"Cheesecake Turtle\"}");
const fd10=JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0606},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":1.3636},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0327},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.5455},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":1.2121},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.1364},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.303},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":4.8485},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.3636},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0909}],\"foreignIDs\":{\"Nutritionix\":\"56880641a1b8c86412aa5557\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/568806cf2952f850022f929b.jpeg\",\"names\":{\"en\":\"Dark Almond Chocolate\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"pieces\":1,\"grams\":33},\"intolerances\":[],\"name\":\"Dark Almond Chocolate\"}");
const fd11=JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":121.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":5.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":1060.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":91.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":16.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":135.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":59.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":540.0},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":21.0}],\"foreignIDs\":{\"Nutritionix\":\"ddee6761f35e01abac7edcfa\"},\"photo\":\"/img/default_food_image.png\",\"names\":{\"en\":\"Turtle Sundae\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"grams\":0,\"serving\":1},\"intolerances\":[],\"name\":\"Turtle Sundae\"}");
const fd12=JSON.parse("{\"consumableID\":0,\"expTimeAfterOpen\":0,\"nutrients\":[{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":269,\"usda_tag\":\"SUGAR\",\"name\":\"Sugars, total\",\"unit\":\"gram\"},\"quantity\":0.2941},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":291,\"usda_tag\":\"FIBTG\",\"name\":\"Fiber, total dietary\",\"unit\":\"gram\"},\"quantity\":0.0118},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":307,\"usda_tag\":\"NA\",\"name\":\"Sodium, Na\",\"unit\":\"mili gram\"},\"quantity\":2.4706},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":318,\"usda_tag\":\"VITA_IU\",\"name\":\"Vitamin A, IU\",\"unit\":\"international Unit\"},\"quantity\":3.5294},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":208,\"usda_tag\":\"ENERC_KCAL\",\"name\":\"Energy\",\"unit\":\"Kcal\"},\"quantity\":3.2941},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":303,\"usda_tag\":\"FE\",\"name\":\"Iron, Fe\",\"unit\":\"mili gram\"},\"quantity\":0.0169},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":203,\"usda_tag\":\"PROCNT\",\"name\":\"Protein\",\"unit\":\"gram\"},\"quantity\":0.0588},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":204,\"usda_tag\":\"FAT\",\"name\":\"Total lipid (fat)\",\"unit\":\"gram\"},\"quantity\":0.1765},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":606,\"usda_tag\":\"FASAT\",\"name\":\"Fatty acids, total saturated\",\"unit\":\"gram\"},\"quantity\":0.0824},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":301,\"usda_tag\":\"CA\",\"name\":\"Calcium, Ca\",\"unit\":\"mili gram\"},\"quantity\":0.7059},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":601,\"usda_tag\":\"CHOLE\",\"name\":\"Cholesterol\",\"unit\":\"mili gram\"},\"quantity\":0.6471},{\"nutrientWrapperID\":0,\"nutrient\":{\"tag\":205,\"usda_tag\":\"CHOCDF\",\"name\":\"Carbohydrate, by difference\",\"unit\":\"gram\"},\"quantity\":0.4118}],\"foreignIDs\":{\"Nutritionix\":\"52ae479aaf5a0bb91c015136\"},\"photo\":\"https://d1r9wva3zcpswd.cloudfront.net/536bbbb14a749f8f03d612f7.jpeg\",\"names\":{\"en\":\"Cheesecake\"},\"origin\":[\"BRANDED\"],\"servingSizes\":{\"slice\":1,\"grams\":85},\"intolerances\":[],\"name\":\"Cheesecake\"}");

var detailedArray = [ fd1,fd2,fd3,fd4,fd5,fd6,fd7,fd8,fd9,fd10,fd11,fd12];

var protein,fat,carbohydrate,energy;
var otherNuts = [];
var selectedFood;
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
}

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
    listRow.setAttribute("onclick","getFoodDetails(\"" + item.foodID + "\",\"" + item.dbName + "\")");
    listRow.setAttribute("style","padding: 2px;");

    if(item.photo === "/img/default_food_image.png")
        listRow.innerHTML = "<span>" + item.name + "</span>" +
            "<img class='imageClass d-inline-block mr-1' src=../../static/img/default_food_image.png" +  " alt=\"" + item.name + "\"/>";
    else
        listRow.innerHTML = "<span>" + item.name + "</span>" +
            "<img class='imageClass d-inline-block mr-1' src=" + item.photo +  " alt=\"" + item.name + "\"/>";

    document.getElementById('result-tab').appendChild(listRow);
}

// creates results list
function showResults() {
    var warningSpan = document.getElementById('search-warning-span');

    //if (document.getElementById('searchInput').value.length >= 3) {
    warningSpan.setAttribute('style','display:none;');
    document.getElementById('result-tab').innerHTML = "";
    let i = 0;
    searchResultArray.forEach(function (element) {
        i++;
        renderItemList(element, i);
    });
// else {
//         if(warningSpan.innerText.length === 0 )
            //warningSpan.setAttribute('style','');
//     }
}


function getFoodDetails(id, dbName) {
    otherNuts = []; //clear table

    //replace with AJAX
    for (let i = 0 ; i < detailedArray.length; i++) {

        if (detailedArray[i].foreignIDs[dbName] === id) {
            selectedFood = detailedArray[i];
            break;
        }
    }

    selectedFood.nutrients.forEach( function(nw) {
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
    });



    //clear results tab
    document.getElementById('result-tab-content').innerHTML ="";

    // print chart and compute statistics if food contains required information
    if(carbohydrate !== null && protein !== null && fat !== null) {
        printChart(carbohydrate,protein,fat);

        var nutrSum = fat.value + protein.value + carbohydrate.value;
        var fPerc = fat.value/nutrSum;
        var pPerc = fat.value/nutrSum;
        var cPerc = fat.value/nutrSum;
    }

    // render empty nutrients table
    var detailsDiv = document.createElement('div');
    detailsDiv.className = "tab-pane show active";
    detailsDiv.setAttribute("id","result-list-item-1-details");
    detailsDiv.setAttribute("role","tabpanel");
    detailsDiv.setAttribute("aria-labelledby","result-list-item-1");
    detailsDiv.setAttribute("style","display:table; margin:0 auto;"); //center table on smaller screens
    detailsDiv.innerHTML= "        <table class='table-striped'>\n" +
        "            <thead>\n" +
        "                <tr>\n" +
        "                    <th th:text='#{name}'>Name</th>\n" +
        "                    <th th:text='#{unit}'>unit</th>\n" +
        "                    <th th:text='#{quantity}'>quantity</th>\n" +
        "                </tr>\n" +
        "            </thead>\n" +
        "            <tbody id='nutrients-table-body'>\n" +
        "            </tbody>\n" +
        "        </table>";
    document.getElementById('result-tab-content').appendChild(detailsDiv);

    //fill table with data
    var rows="";
    for(let i=0;i<otherNuts.length;i++) {
        rows = rows +  "<tr>\n" +
            "<td style='text-align: left;'>" + otherNuts[i].name + "</td>" +
            "<td>" + otherNuts[i].unit + "</td>" +
            "<td>" + otherNuts[i].value+ "</td>" +
            "\n</tr>";
    }
    document.getElementById('nutrients-table-body').innerHTML=rows;
    displayEnergy();

    renderServingUnits();
    document.getElementById('serving_size').value = selectedFood.servingSizes["grams"];


}

function updateDetails() {
    const quantity = $("#serving_size").val();
    const unit = $("#serving_unit").val();
    var uVal;

    if(unit === "G")
        uVal = 1;
    else
        uVal = selectedFood.servingSizes[unit];

    qt = quantity*uVal /selectedFood.servingSizes["grams"];

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

    if(currentTime >= 19 || currentTime <= 4)
        document.getElementById('supper').className = "btn btn-success p-1 active";
    else if(currentTime > 4 && currentTime < 11 )
        document.getElementById('breakfast').className = "btn btn-success p-1 active";
    else if(currentTime >= 11 && currentTime <= 14 )
        document.getElementById('lunch').className = "btn btn-success p-1 active";
    else
        document.getElementById('dinner').className = "btn btn-success p-1 active";
}

function logFood() {
    var food = {};
    food.serving_unit = document.getElementById("serving_unit")
        .options[document.getElementById("serving_unit").selectedIndex].value;
    food.entity = selectedFood;
    food.serving_size = document.getElementById("serving_size").value;
    food.time = document.getElementById("diary-time").value;
    food.date = document.getElementById("diary-date").value;

    const mealRadios = document.getElementById('diary-meal');
    let mealLabel =  mealRadios.getElementsByClassName('active')[0];
    food.meal = mealLabel.firstElementChild.value;
    //send via AJAX
    $(function() {

        $('#logged-success').fadeIn('slow', function(){
            $('#logged-success').delay(3000).fadeOut();
        });
    });
}

/*
Other miscellaneous
 */
// Run with dummy data
$(document).ready( function() {
    getFoodDetails("53304272044079d310ba24ed", "Nutritionix");
});