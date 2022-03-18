    $(document).ready(function(){

	    var grid=$("#grid-data").bootgrid({
	    	navigation:2,
  			columnSelection:false,
		    ajax:true,
		    url:"listAllTasks",
		    formatters: {
		    "taskcreatetime":function(column, row){
		    	return getLocalTime(row.taskcreatetime);
		    },
		    "commands": function(column, row)
		    {
		       return "<button class=\"btn btn-xs btn-default ajax-link command-run1\" disabled=\"disabled\" data-row-id=\"" + row.taskId + "\">处理</button>";
		    }
	    	}
	    }).on("loaded.rs.jquery.bootgrid", function()
	    		{
	    	    grid.find(".command-run1").on("click", function(e)
	    	    {
					var taskid=$(this).data("row-id");
					// 获取formkey的值动态加载对应的表单
					/**
					$.get("getFormInfo/"+taskid, function (d){
						$("#box-content").load(d.msg);
						$.post("dealtask",{"taskid":taskid},function(data){
							var obj = data;
							$("#reason").val(obj.reason);
							$("#type").val(obj.leave_type);
							$("#userid").val(obj.user_id);
							$("#startime").val(obj.start_time);
							$("#endtime").val(obj.end_time);
							$("#applytime").val(obj.apply_time);
							$("form").attr("action","task/deptcomplete/"+taskid);
							$("form").data("taskid",taskid);
						});
					});
					**/



	    	    });
	    });
	  });
	  
	  
function getLocalTime(nS) {  
 return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');  
}
