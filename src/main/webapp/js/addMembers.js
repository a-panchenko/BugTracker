function addMembers(id) {
    var users = [];
    $("#users-to-select").find("input:checkbox:checked").each(function () {
        users.push($(this).val());
    });

    var url = "addprojectmembers";

    $.ajax({
        url: url,
        type: "POST",
        data: {
            id: id,
            users: users
        },
        success: function() {
            getProjectMembers(id)
        },
        error: function() {
            console.error("Error while add members");
        }
    });
}