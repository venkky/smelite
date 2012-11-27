	$("#vendors").click(function(){
		$.ajax({url:"SmliteController/vendor.json",
			dataType:"json",
			data:{				
				bustcache:new Date().getTime()
			},			
			success:function(data){
				var vendors = data.vendor_collection;
				var html="<legend>Vendors</legend>";
				html += "<table class=table table-hover>";
				html += "<thead>"+
							"<tr>"+
								"<th>Vendor</th>"+
								"<th>PO Created</th>" +
								"<th>PO Number</th>" +
								"<th>PO Type</th>" +
								"<th>PO Details</th>" +
								"<th>Action</th>" +
							"</tr>"+
						"</thead>";
				html += "<tbody>";
				for(var i=0;i<vendors.length;i++){
					var vendor = vendors[i];
					html += "<tr>";
					html += "	<td>"+vendor.name+"</td>";
					html += "	<td>"+vendor.pOCreated+"</td>";
					html += "	<td>"+vendor.poNumber+"</td>";
					html += "	<td>"+vendor.poType+"</td>";
					html += "	<td>"+vendor.poDetails+"</td>";
					html += "	<td><a href='open_vendor.jsp?id="+vendor.id+"'>Open</a></td>";
					html += "</tr>";
				}
				html += "	</tbody>";
				html += "</table>";
				$("#main").html(html);
			}
		});
	});
	
	$("#create").click(function(){
		var html="<legend>Create Vendor</legend>";
		html += "<form class='form-horizontal'>" +
				"<table class=table table-hover>" +
				"	<tr>" +
				"		<td>Vendor Name</td>" + 
				"		<td><input type='text' id='name'></td>" +
				"	</tr>" +
				"	<tr>" +
				"		<td>PO Created</td>" +
				"		<td><input type='checkbox' id='pOCreated'></td>" +
				"	</tr>" +
				"	<tr>" +
				"		<td>PO Number</td>" +
				"		<td><input type='text' id='poNumber'></td>" +
				"	</tr>" +
				"	<tr>" +
				"		<td>PO Type</td>" +
				"		<td>" +
				"			<select id='poType'>" +
				"				<option>Staff Augmentation</option>" +
				"				<option>Outbound Projects</option>" +
				"				<option>Software Licensing</option>" +
				"				<option>Hardware Purchase</option>" +
				"			</select>" +
				"		</td>" +
				"	</tr>" +
				"	<tr>" +
				"		<td>PO Details</td>" +
				"		<td><textarea multiline='true' id='poDetails' rows='4' cols='25'/></td>" +
				"	</tr>" +
				"	<tr>" +
				"		<td colspan='2'>" +
				"			<button class='btn btn-info' id='cancel'>Cancel</button>" +
				"			<button class='btn btn-info' id='save'>Save</button>" +
				"		</td>" +
				"	</tr>" +
				"</table>" +
				"</form>";
		$("#main").html(html);
	});
	
	$("#projections").click(function(){
		$.ajax({url:"SmliteController/spendprojection/spend.json",
			dataType:"json",
			data:{				
				bustcache:new Date().getTime()
			},	
			success:function(data){
				var html="<legend>Spend Projection</legend>";
				html += "<table class=table table-hover>";
				html += "<thead>"+
							"<tr>"+
								"<th>Current Month Expenditure</th>"+
								"<th>Quarterly Projection</th>" +
								"<th>Yearly Projection</th>" +
							"</tr>"+
						"</thead>";
				html += "<tbody>";
				html += "<tr>";
				html += "	<td>"+data.currentMonthExpenditure+"</td>";
				html += "	<td>"+data.querterlyProjection+"</td>";
				html += "	<td>"+data.yearlyProjection+"</td>";
				html += "</tr>";
				html += "<tr>";
				html += "	<td colspan='3'><button class='btn btn-info' id='back'>Back</button></td>";
				html += "</tr>";
				html += "</tbody>";
				html += "</table>";
				$("#main").html(html);
			}
		});
	});
	
	$("#save").live("click", function(){
		var vendor={};
		if($("#pOCreated:checked").size()==1){
			vendor= {	name: $("#name").val(),
			  		pOCreated:true,
			  		poDetails: $("#poDetails").val(),
			  		poNumber:$("#poNumber").val(),
			  		poType:$("#poType").val()
			};
		}else{
			vendor = {	name: $("#name").val(),
			  		pOCreated:false,
			  		poDetails: "",
			  		poNumber:0,
			  		type:""
			};
		}
		$.ajax({
			type: 'POST',
			url: "SmliteController/vendor/new",
			data: vendor,
			success: function(data){
				$("#vendors").click();
			}
		});
		return false;
	});
	
	
	$("#update").live("click", function(){
		var vendor={};
		if($("#pOCreated:checked").size()==1){
			vendor= {	name: $("#name").val(),
			  		pOCreated:true,
			  		poDetails: $("#poDetails").val(),
			  		poNumber:$("#poNumber").val(),
			  		poType:$("#poType").val()
			};
		}else{
			vendor = {	name: $("#name").val(),
			  		pOCreated:false,
			  		poDetails: "",
			  		poNumber:0,
			  		type:""
			};
		}
		$.ajax({
			type: 'POST',
			url: "/smelite/SmliteController/batch?_refs=/smelite/vendor/" + $("#id").val() + "/edit&_method=PUT",
			data: vendor,
			success: function(data){
				$("#vendors").click();
			}
		});
		return false;
	});
	
	$("#delete").live("click", function(){
		var option = confirm("Do you really want to delete vendor " + $("#name").val() + "?");
		if(!option){
			return false;
		}
		$.ajax({
			type: 'POST',
			url: "/smelite/SmliteController/batch?_refs=/smelite/vendor/" + $("#id").val() + "/delete&_method=DELETE",
			success: function(data){
				$("#vendors").click();
			}
		});
		return false;
	});
	
	$("#back").live("click", function(){
		$("#vendors").click();
	});
	
	
	function openVendor(){
		$.ajax({url:"SmliteController/vendor/" + $("#id").val() + ".json",
			dataType:"json",
			data:{				
				bustcache:new Date().getTime()
			},
			success:function(data, textStatus, jqXHR){
				var html="<legend>Open Vendor</legend>";					
					html += "<input type='hidden' value='" + data.id + "'>" +
						"<table class=table table-hover>" +
						"	<tr>" +
						"		<td>Vendor Name</td>" + 
						"		<td><input type='text' id='name' value='" + data.name + "'></td>" +
						"	</tr>" +
						"	<tr>" +
						"		<td>PO Created</td>" +
						"		<td><input type='checkbox' id='pOCreated' checked='" + data.pOCreated + "'></td>" +
						"	</tr>" +
						"	<tr>" +
						"		<td>PO Number</td>" +
						"		<td><input type='text' id='poNumber' value='" + data.poNumber + "'></td>" +
						"	</tr>" +
						"	<tr>" +
						"		<td>PO Type</td>" +
						"		<td>" +
						"			<select id='poType'>" + 
										selectBuilder('Staff Augmentation', data.poType) +
										selectBuilder('Outbound Projects', data.poType) +
										selectBuilder('Software Licensing', data.poType) +
										selectBuilder('Hardware Purchase', data.poType) +
						"			</select>" +
						"		</td>" +
						"	</tr>" +
						"	<tr>" +
						"		<td>PO Details</td>" +
						"		<td><textarea multiline='true' id='poDetails' rows='4' cols='25'/></td>" +
						"	</tr>" +
						"	<tr>" +
						"		<td colspan='2'>" +
						"			<button class='btn btn-info' id='back'>Cancel</button>" +
						"			<button class='btn btn-info' id='update'>Save</button>" +
						"			<button class='btn btn-info' id='delete'>Delete</button>" +
						"		</td>" +
						"	</tr>" +
						"</table>";
				$("#main").html(html);
			}
		});
	};
	
	function selectBuilder(option, value){
		var html = "<option>" + option  + "</option>";
		if(option == value){
			html = "<option selected>" + option  + "</option>";
		}
		return html;
	};
	