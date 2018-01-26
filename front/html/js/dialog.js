$(function() {
	$("#reset_dialog").click(function() {
        console.log("パスワードリセット");
		$("#reset_pass").dialog({
			modal:true, 
            title:"パスワードを忘れた場合",
            width:500,
			buttons: { 
                "パスワードリセット": function() {
                    console.log($("#employee_id").val());
                    console.log($("#mail").val());
                    alert();
                    $(this).dialog("close");
                },
                "キャンセル": function() {
                    $(this).dialog("close"); 
                }
            }
        });
	});
});

function alert(){
    $("#alert").dialog({
        modal:true, 
        width:500,
		buttons: { 
           "OK": function() {
               $(this).dialog("close");
            }
        }
    });
}