function getProjectMembers(id) {
    $("#members").empty();

    var url = "projectmembers";
    $.ajax({
        dataType: "xml",
        url: url,
        type: "GET",
        data: "id=" + id,
        success: function(data) {
            $(data).find("member").each(function() {
                name = $(this).find("name").text();
                group = $(this).find("group").text();
                $("#members").append("<br><input type='checkbox' onchange='removeProjectMember(" + id + ", value)' checked value='" + name + "'>"
                        + name + "(" + group + ")" + "</checkbox>");
            })
        },
        error: function() {
            console.error("Error while getting members");
        }
    });
}

function removeProjectMember(id, member) {
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
