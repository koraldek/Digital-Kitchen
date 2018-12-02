// Change language
$(function(){
    $('.locales').selectpicker();
});

$(document).ready(function() {
    $(".locales").change(function () {
        const selectedOption = $('.locales').val();
        if (selectedOption !== '')
            window.location.replace('?lang=' + selectedOption);
    });
});



// search food
$(document).ready(function() {
    $('#searchInput').autocomplete({
        minLength: 2,
        delay:400,
        source: function(request, response) {
            $.ajax({
                type: "GET",
                url: '/api/food/autocomplete',
                dataType:'json',
                data: { keyword:  $('#searchInput').val()},
                cache: true,
                success: function( data ) {
                    console.log('Response from server:' +  JSON.stringify(data));
                    // response(
                    //     $.map( data, function( item ) {
                    //     return {
                    //         data: item.foodID,
                    //         value: item.name
                    //     }
                    // }));
                    response(data)
                },
                error: function (error) {
                    alert('error in autocomplete: ' + error.text);
                }

            });
        },
        focus: updateTextBox,
        select: updateTextBox

    }).autocomplete('instance')._renderItem = function (ul, item) {
        return $('<li>')
            .append("<img class='imageClass' src=" + item.photo + " alt=" + item.name + "/>")
            .append('<a th:href="@{/food/detailed/" href="">' + item.name + '</a>')
            .appendTo(ul); };

    function updateTextBox(event, ui) {
        $(this).val(ui.item.Name);
        return false;
    }

});