$(function() {
	$("#reset_dialog").click(function() {
        console.log("パスワードリセット");
		$("#reset_pass").dialog({
            modal:true,
            resizable:false, 
            title:"パスワードを忘れた場合",
            width:500,
			buttons: { 
                "パスワードリセット": function() {
                    console.log($("#employee_id").val());
                    console.log($("#mail").val());
                    // $("#reset_pass_form").submit();
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
        width:300,
		buttons: { 
           "OK": function() {
               $(this).dialog("close");
            }
        }
    });
}