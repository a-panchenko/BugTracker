function getReplies(issueId) {
    var url = "getreplies";
    $.ajax({
        dataType: "json",
        url: url,
        type: "GET",
        data: "id=" + issueId,
        success: function(data) {
            if (data.length > 0) {
                $("#replies").empty();
                $("#replies").append("<p>Replies:<table border='1' width='80%'><tr><td>Date</td><td>Posted By</td><td width='80%'>Message</td></tr></table></p>");
                $.each(data, function (i, item) {
                    date = item.date;
                    poster = item.poster;
                    message = item.message;
                    $("#replies table").append("<tr><td>" + date + "</td><td>" + poster + "</td><td>" + message + "</td></tr>");
                });
            }
        },
        error: function() {
            console.error("Error while getting replies");
        }
    });
}

function addReply(issueId) {
    var url = "addreply";
    $.ajax({
        url: url,
        type: "POST",
        data: {
            issueId : issueId,
            message : $("#message").val()
        },
        error: function() {
            console.error("Error while add reply");
        }
    });
    getReplies(issueId);
}