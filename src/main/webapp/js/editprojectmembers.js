function addMembers(id) {
    var users = [];
    $("#users-to-select").find("input:checkbox:checked").each(function() {
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

function getProjectMembers(id) {
    var url = "projectmembers";
    $.ajax({
        dataType: "xml",
        url: url,
        type: "GET",
        data: "id=" + id,
        success: function(data) {
            $("#members").empty();
            $(data).find("member").each(function() {
                name = $(this).find("name").text();
                group = $(this).find("group").text();
                $("#members").append("<br><input type='checkbox' onclick='removeProjectMember(" + id + ", value)' checked value='" + name + "'>"
                + name + "(" + group + ")" + "</checkbox>");
            })
        },
        error: function() {
            console.error("Error while getting members");
        }
    });
}

function removeProjectMember(id, member) {
    if (confirm("Remove member from project?")) {
        var url = "removeprojectmember";
        $.ajax({
            url: url,
            type: "GET",
            data: "id=" + id + "&" + "member=" + member,
            success: function() {
                $("#members input[type=checkbox][value=" + member + "]")[0].previousSibling.remove();
                $("#members input[type=checkbox][value=" + member + "]")[0].nextSibling.remove();
                $("#members input[type=checkbox][value=" + member + "]").remove();
            },
            error: function() {
                console.error("Error while remove member");
            }
        });
    }
    else {
        $("#members input[type=checkbox][value=" + member + "]").prop("checked", true);
    }
}

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
