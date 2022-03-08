    $(document).ready(function(){
    	$("#pm").hide();
	    var grid=$("#grid-data").bootgrid({
	    	navigation:2,
  			columnSelection:false,
		    ajax:true,
		    url:"pmtasklist",
		    formatters: {
		    "taskcreatetime":function(column, row){
		    	return getLocalTime(row.taskcreatetime);
		    },
		    "commands": function(column, row)
		    {
		        return "<button class=\"btn btn-xs btn-default ajax-link command-run1\" data-projectname="+row.projectName+" data-starttime=\""+ row.startTime + "\" data-content="+ row.content+ " data-endtime=\""+ row.endTime + "\" data-applyer="+row.applyer+" data-row-id=\"" + row.taskid + "\">处理</button>";
		    }
	    	}
	    
	    }).on("loaded.rs.jquery.bootgrid", function()
	    		{
	    	    grid.find(".command-run1").on("click", function(e)
	    	    {
	    	    	var taskid=$(this).data("row-id");
	    	    	$("#projectName").val($(this).data("projectname"));
	    	    	$("#userid").val($(this).data("applyer"));
					$("#startTime").val($(this).data("starttime"));
					$("#endTime").val($(this).data("endtime"));
					$("#content1").val($(this).data("content"));
	    	    	$("#pm").show();
	    	    	$("form").data("taskid",taskid);
	    	    });
	    });
	  });
	  
function getLocalTime(nS) {  
 return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');  
}
