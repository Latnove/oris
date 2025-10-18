<#include "base.ftl">

<#macro title>upload</#macro>

<#macro scripts></#macro>

<#macro content>
    <h1>ЗАГРУЗИ ФАЙЛ</h1>
    <form id="my_form" class="form" method="POST" action="upload" enctype="multipart/form-data">
        <input type="file" name="file"/>
    </br>
    <input type="submit" value="file bro">
    </form>
</#macro>