function searchMembers() {
    var url = "groupmembers";
    $("#users-to-select").empty();
    $.ajax({
        dataType: "xml",
        url: url,
        type: "GET",
        data: "name=" + $('#search').val()+ "&" + "group=" + $('#group').val(),
        success: function(data) {
            $(data).find("name").each(function() {
                $("#users-to-select").append("<br><input type='checkbox' value='" + $(this).text() + "'/>" + $(this).text());
            })
        },
        error: function() {
            console.error("Error while getting members");
        }
    });
}
