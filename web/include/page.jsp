<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="pg" value="${pages}"/>
<script>
    function reflush() {
        var p = $("input[name='radioPages']:checked").val();
        $("input[name='pageCount']").val(p);
        $("input[name='currentPage']").val(1);

        document.forms[0].action="list";
        document.forms[0].submit();
    }
    function gotoPages(pages){
        $("input[name='currentPage']").val(pages);
        document.forms[0].action="list";
        document.forms[0].submit();
    }
    function checkPages(pages,minPages,maxPages){
        if(pages < minPages || pages > maxPages){
            alert("页码超出范围，请注意!");
        }else{
            gotoPages(pages);
        }
    }
</script>

<input id="currentPage" name="currentPage" type="hidden" value="${currentPage}"/>
<input id="pageCount" name="pageCount" type="hidden" value="${pageCount}"/>

<ul id="example"></ul>
<script>
    var options = {
        bootstrapMajorVersion: 3,
        currentPage: ${pg['currentPage']},
        totalPages: ${pg['pages']},
        numberOfPages:5,
        useBootstrapTooltip:true,
        tooltipTitles: function (type, page, current) {
            switch (type) {
                case "first":
                    return "跳转至首页 <i class='icon-fast-backward icon-white'></i>";
                case "prev":
                    return "跳转至上一页 <i class='icon-backward icon-white'></i>";
                case "next":
                    return "跳转至下一页 <i class='icon-forward icon-white'></i>";
                case "last":
                    return "跳转至尾页 <i class='icon-fast-forward icon-white'></i>";
                case "page":
                    return "跳转至第 " + page + "页 <i class='icon-file icon-white'></i>";
            }
        },
        pageUrl: function (type, page, current) {
            return "javascript:gotoPages("+page+")";
        },
        bootstrapTooltipOptions: {
            html: true,
            placement: 'bottom'
        }
    }


    $(document).ready(function(){
        $("input[name='radioPages'][value='${pageCount}']").parent('label').addClass('active');
        //$("input[name='radioPages'][value='<s:property value='pages'/>']").attr("checked",true);
        //$("input[name='radioPages']:checked").parent('label').addClass('active');
        $('#example').bootstrapPaginator(options);
    });

</script>
  
