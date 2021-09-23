<div class="Yqybanner section_fr" >
    <div class="section_s">
        <div class="posiR categories fl">
            <input type="text" placeholder="输入美食" value="" class="input">
            <div class="posiA categories_div">
                <#--<ul>-->
                    <#--<li>-->
                        <#--<label class="fl"><strong>厦门</strong>鼓浪屿</label>-->
                        <#--<span class="fr">约3911个结果</span>-->
                    <#--</li>-->
                    <#--<li>-->
                        <#--<label class="fl"><strong>厦门</strong>环岛路</label>-->
                        <#--<span class="fr">约3911个结果</span>-->
                    <#--</li>-->
                    <#--<li>-->
                        <#--<label class="fl"><strong>厦门</strong>白城</label>-->
                        <#--<span class="fr">约3911个结果</span>-->
                    <#--</li>-->
                    <#--<li>-->
                        <#--<label class="fl"><strong>厦门</strong>大学</label>-->
                        <#--<span class="fr">约3911个结果</span>-->
                    <#--</li>-->
                <#--</ul>-->
            </div>
        </div>
        <a href="javaScript:HeaderSearch();" class="but fr"></a>
    </div>

    <div class="slide-main" id="touchMain">
        <div class="slide-box textC" id="slideContent">
            <div class="slide"><a stat="sslink-1" href="Food_List.html" target="_blank"><img data-original="images/Nbanner4.jpg" /></a></div>
            <div class="slide"><a stat="sslink-2" href="Food_List.html" target="_blank"><img data-original="images/Nbanner1.jpg" /></a></div>
        </div>
        <div class="item">
            <a class="cur" stat="item1001" href="javascript:;"></a><a href="javascript:;" stat="item1002"></a>
        </div>
    </div>
</div>




<div >
    <h1 >广告</h1>
<#list adses as ads>
    <div>${ads.url}</div>
</#list>
</div>