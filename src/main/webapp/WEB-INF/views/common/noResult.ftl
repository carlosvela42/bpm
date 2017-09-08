<script type="text/javascript">
	var colCount = 0;
	$('tr:nth-child(1) td').each(function () {
	    if ($(this).attr('colspan')) {
	        colCount += +$(this).attr('colspan');
	    } else {
	        colCount++;
	    }
	});
	var html = "<tr>";
	html += "<td colspan=" + colCount + ">${e.get('no.result')}</td>";
	html += "</tr>";
	$(".table_body").html(html);
</script>