<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>公司定制旅游_个人定制旅游_家庭定制旅游-旅行帮</title>
    <meta name="keywords" content="公司定制旅游,个人定制旅游,家庭定制旅游,公务定制考察,会议定制,尾牙定制,亲子定制旅游,周边定制旅游,自驾定制旅游,蜜月定制旅游,毕业定制旅游" />
    <meta name="description" content="量身定制的旅游方案,时尚创意的旅行路线,深入体验的定制专家,私人定制的专属行程，定制旅游尽在旅行帮" />
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/newcss/dingzhi_index.css" rel="stylesheet" type="text/css">
    <link href="/newcss/dingzhi_layer.css" rel="stylesheet" type="text/css">
	<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .categories_Addmore2{top:33px;left:-1px;}
        .categories_div{top:32px;left:-1px;}
        .tooltip.bottom .tooltip-inner {background-color:rgb(168,206,229);color:#666666;}
        .tooltip.bottom .tooltip-arrow {border-bottom-color:rgb(168,206,229);}
    </style>
</head>
<body myname="customRequire">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>

<div class="main cl nb_body" style="background: url(http://images.tuniucdn.com/img/201504231415/helpcow/pathbg.jpg) center top no-repeat;">
      <div class="w1190 cl">
		  <!-- 定制需求单 开始 -->
		  <!--牛帮头部 S-->
		  <div class="nb_hd">
			  <h2>Hi ${userName}</h2>
			  <h3>请花2分钟时间认真回答如下问题，找到最合适的小帮，小帮很忙，一天只能找他们帮忙两次哦！</h3>
		  </div>
		  <!--牛帮头部 E-->
		  <div class="nb_main clearfix">
			  <!--牛帮问题区 S-->
			  <div class="nb_quest">
				  <!--牛帮问题进度提示 S-->
				  <div class="flex_wrap nb_steps">
					  <i class="nb_icon nb_step_line"></i>
					  <div id="J_OnePage_Title" class="flex_item nb_step nb_step_1 nb_step_0 nb_step_current">
						  <div class="nb_icon nb_step_circle">
							  <i class="nb_icon nb_icon_able"></i>
						  </div>
						  <div class="nb_step_tit">基本出游信息</div>
						  <i class="nb_icon nb_step_arrow J_IconCircle"></i>
					  </div>
					  <div id="J_TwoPage_Title" class="flex_item nb_step nb_step_2 nb_step_0 nb_step_current">
						  <div class="nb_icon nb_step_circle">
							  <i class="nb_icon nb_icon_able"></i>
						  </div>
						  <div class="nb_step_tit">出游人数及预算</div>
						  <i class="nb_icon nb_step_arrow J_IconCircle nb_hide"></i>

					  </div>
					  <div id="J_ThreePage_Title" class="flex_item nb_step nb_step_3 nb_hide">
						  <div class="nb_icon nb_step_circle">
							  <i class="nb_icon"></i>
						  </div>
						  <div class="nb_step_tit">住宿和景点要求</div>
						  <i class="nb_icon nb_step_arrow nb_hide J_IconCircle"></i>

					  </div>
					  <div id="J_FourPage_Title" class="flex_item nb_step nb_step_4 nb_hide">
						  <div class="nb_icon nb_step_circle">
							  <i class="nb_icon"></i>
						  </div>
						  <div class="nb_step_tit">其他补充</div>
						  <i class="nb_icon nb_step_arrow nb_hide J_IconCircle"></i>

					  </div>
					  <div id="J_FivePage_Title" class="flex_item nb_step nb_step_5 nb_step_current">
						  <div class="nb_icon nb_step_circle">
							  <i class="nb_icon nb_icon_able"></i>
						  </div>
						  <div class="nb_step_tit">个人信息</div>
						  <i class="nb_icon nb_step_arrow J_IconCircle nb_hide"></i>
					  </div>
				  </div>
				  <!--牛帮问题进度提示 E-->
				  <!--牛帮问题页 S-->
				  <div class="nb_pages">
					  <!--page 1 S-->
					  <div class="nb_page J_OnePage" style="display: block;">
						  <div class="nb_page_hd"><span>*</span>这次出游你想要小帮帮你？</div>
						  <div class="flex_wrap nb_page_item nb_page_item_single">
							  <div class="flex_item nb_page_field" id="J_NBType">
                                  <div class="nb_form_checkbox">
                                      <ul class="flex_wrap">
                                          <li class="flex_item <c:if test="${customType == 'company'}">nb_form_checked</c:if>" data-customType="company">
                                              <a href="javascript:;">公司定制(大于10人)</a>
                                              <i class="nb_icon nb_form_checkbox_icon"></i>
                                          </li>
                                          <li class="flex_item <c:if test="${customType == 'home'}">nb_form_checked</c:if>" data-customType="home">
                                              <a href="javascript:;">家庭定制</a>
                                              <i class="nb_icon nb_form_checkbox_icon"></i>
                                          </li>
										  <li class="flex_item <c:if test="${customType == 'personal'}">nb_form_checked</c:if>" data-customType="personal">
											  <a href="javascript:;">个人定制</a>
											  <i class="nb_icon nb_form_checkbox_icon"></i>
										  </li>
                                      </ul>
                                  </div>
                              </div>
						  </div>
						  <div class="nb_page_hd">你想要的？</div>


						  <div class="flex_wrap nb_page_item nb_hide J_OneMethod">
							  <div class="flex_item nb_page_label"><span>*</span>出游方式：</div>
							  <div class="flex_item nb_page_field nb_relative nb_forIe" id="J_NBMethod"></div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_page_start nb_page_end">
							  <div class="flex_item nb_page_label"><span>*</span>从哪出发：</div>
							  <div class="flex_item nb_page_field">
								  <div class="nb_form_input" style="position:relative;">
                                      <!-- <input id="J_NBStart" type="text" placeholder="请输入您的出发地" autocomplete="off" code="602"> -->
                                      <!-- 出发地搜索面板 开始 -->
									  <input id="J_StartCityId" type="text" placeholder="请输入您的出发地" value=""
											 class="input m0auto mt15 clickinput" data-areaId=""
											 data-url="/lvxbang/destination/getDestSeachAreaList.jhtml"
											 autoComplete="Off"><!-- #BeginLibraryItem "/lbi/destination.lbi" -->
									  <!--目的地 clickinput input01 input-->
									  <div class="posiA Addmore categories_Addmore2">
										  <i class="close"></i>
										  <!--<div class="Addmore_d">
                                              搜索历史：<span>厦门</span>
                                          </div>-->
										  <dl class="Addmore_dl">
											  <dt>
											  <div class="Addmore_nr check_wid">
												  <ul>
													  <li class="checked"><a href="javaScript:;">热门</a></li>
													  <li><a href="javaScript:;">A-E</a></li>
													  <li><a href="javaScript:;">F-J</a></li>
													  <li><a href="javaScript:;">K-P</a></li>
													  <li><a href="javaScript:;">Q-W</a></li>
													  <li><a href="javaScript:;">X-Z</a></li>
												  </ul>
											  </div>
											  </dt>
											  <dd>
												  <label></label>
												  <div class="Addmore_nr">
													  <ul>
                                                          <c:forEach items="${destinations}" var="aArea">
                                                          <li data-id="${aArea.id}">
                                                              <a href="javaScript:;">${aArea.name}</a>
                                                          </li>
                                                          </c:forEach>
													  </ul>
												  </div>
											  </dd>
                                              <c:forEach items="${letterSortAreas}" var="letterSortArea">
                                              <dd class="disn">
                                                  <c:forEach items="${letterSortArea.letterRange}" var="lrArea">

                                                  <div class="Addmore_nr clearfix">
													  <label>${lrArea.name}</label>
                                                      <ul class="clearfix">
                                                          <c:forEach items="${lrArea.list}" var="aArea">
                                                          <li data-id="${aArea.id}">
                                                              <a href="javaScript:;">${aArea.name}</a>
                                                          </li>
                                                          </c:forEach>
                                                      </ul>
                                                  </div>
                                                  </c:forEach>
                                              </dd>
                                              </c:forEach>
										  </dl>
										  <p class="cl"></p>
									  </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
									  <!--关键字提示 clickinput input input01-->
									  <div class="posiA categories_div  KeywordTips">
										  <ul>

										  </ul>
									  </div>

									  <!--错误-->
									  <div class="posiA categories_div cuowu textL">
										  <p class="cl">抱歉未找到相关的结果！</p>
									  </div>
									  <!-- 出发地搜索面板 结束 -->
								  </div>
							  </div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_page_end">
                              <div class="flex_item nb_page_label"><span>*</span>到哪儿玩：</div>
                              <div class="flex_item nb_page_field J_Spot">
                                  <span class="J_Destions">
                                      <!--<div class="flex_item nb_form_btn nb_form_selected J_GoWhere" data-areaId="000000">
                                          <span>泰国</span><a class="" href="javascript:;"><i class="nb_icon J_FirDel"></i></a>
                                      </div>-->
                                  </span>
                                  <div class="flex_item nb_form_input nb_mrb8 J_AddDestion" style="position: relative;">
                                      <!--<input id="J_NBDest" type="text" placeholder="请输入您想去的地点" autocomplete="off">-->
                                      <!-- 目的地搜索面板 开始 -->
                                      <input id="J_Destion" type="text" placeholder="请输入您想去的地点" value="" data-custom-require="true"
                                             class="input m0auto mt15 clickinput" data-areaId=""
                                             data-url="/lvxbang/destination/getDestSeachAreaList.jhtml"
                                             autoComplete="Off"><!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                                      <!--目的地 clickinput input01 input-->
                                      <div class="posiA Addmore categories_Addmore2">
                                          <i class="close"></i>
                                          <!--<div class="Addmore_d">
                                              搜索历史：<span>厦门</span>
                                          </div>-->
                                          <dl class="Addmore_dl">
                                              <dt>
                                              <div class="Addmore_nr check_wid">
                                                  <ul>
                                                      <li class="checked"><a href="javaScript:;">热门</a></li>
                                                      <li><a href="javaScript:;">A-E</a></li>
                                                      <li><a href="javaScript:;">F-J</a></li>
                                                      <li><a href="javaScript:;">K-P</a></li>
                                                      <li><a href="javaScript:;">Q-W</a></li>
                                                      <li><a href="javaScript:;">X-Z</a></li>
                                                  </ul>
                                              </div>
                                              </dt>
                                              <dd>
                                                  <label></label>
                                                  <div class="Addmore_nr Add_wid">
                                                      <ul>
                                                          <c:forEach items="${destinations}" var="aArea">
                                                              <li data-id="${aArea.id}">
                                                                  <a href="javaScript:;">${aArea.name}</a>
                                                              </li>
                                                          </c:forEach>
                                                      </ul>
                                                  </div>
                                              </dd>
                                              <c:forEach items="${letterSortAreas}" var="letterSortArea">
                                                  <dd class="disn clearfix">
                                                      <c:forEach items="${letterSortArea.letterRange}" var="lrArea">

                                                          <div class="Addmore_nr Add_wid">
															  <label>${lrArea.name}</label>
                                                              <ul>
                                                                  <c:forEach items="${lrArea.list}" var="aArea">
                                                                      <li data-id="${aArea.id}">
                                                                          <a href="javaScript:;">${aArea.name}</a>
                                                                      </li>
                                                                  </c:forEach>
                                                              </ul>
                                                          </div>
                                                      </c:forEach>
                                                  </dd>
                                              </c:forEach>
                                          </dl>
                                          <p class="cl"></p>
                                      </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                                      <!--关键字提示 clickinput input input01-->
                                      <div class="posiA categories_div  KeywordTips">
                                          <ul>

                                          </ul>
                                      </div>

                                      <!--错误-->
                                      <div class="posiA categories_div cuowu textL">
                                          <p class="cl">抱歉未找到相关的结果！</p>
                                      </div>
                                      <!-- 目的地搜索面板 结束 -->
                                  </div>
							  </div>
						  </div>

						  <div class="flex_wrap nb_page_item">
							  <div class="flex_item nb_page_label"><span>*</span>玩多久：</div>
							  <div class="flex_item nb_page_field nb_form_field_number" id="J_NBDays">
                                  <div class="flex_wrap nb_form_number">
                                      <div class="flex_item nb_form_btn nb_form_minus">
                                          <a class="J_Minus" href="javascript:;"><i class="nb_icon"></i></a>
                                      </div>
                                      <div class="flex_item nb_form_input"><input id="J_Day" class="J_Number" value="3" type="text"></div>
                                      <div class="flex_item nb_form_btn nb_form_plus">
                                          <a class="J_Plus" href="javascript:;"><i class="nb_icon"></i></a>
                                      </div>
                                  </div>
                              </div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_page_item_startdate">
							  <div class="flex_item nb_page_label"><span>*</span>啥时候出发：</div>
							  <div class="flex_item nb_page_field nb_form_field_select">
								  <div class="nb_form_select nb_time_width">
									  <div class="flex_item nb_form_input nb_relative J_NBTimeIcon">
                                          <i class="time_ico in_time"></i>
                                          <input id="J_StartDate"
                                              type="text" value="" class="in_time" name="startDate"
                                              maxlength="20" placeholder=""
                                              readOnly="true"
                                              onFocus="WdatePicker({onpicked:statusOneNext,minDate:'%y-%M-{%d+1}'})">
									  </div>
								  </div>
							  </div>
						  </div>


						  <div class="flex_wrap nb_page_item nb_page_item_term">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field">
								  <div class="flex_item nb_form_btn nb_form_checkbox_2">
									  <a href="javascript:;" class="J_Adjustment"><i class="nb_icon nb_icon_select"></i></a>
								  </div>
								  <div class="flex_item nb_page_item_term_det">可根据实际情况调整出发日期和天数</div>
							  </div>
						  </div>


						  <div class="flex_wrap nb_page_item nb_page_item_term nb_hide J_PTips">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field">
								  <p class="nb_page_tips"></p>
							  </div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_page_item_term">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field">
								  <input type="button" data-page="J_TwoPage" value="下一步" class="J_NBNext nb_p_prevbtn nb_p_nextbtn nb_unable" name="nbSubmitone" id="J_BtnOneNext" disabled>
							  </div>
						  </div>

					  </div>
					  <!--page 1 E-->
					  <!--page 2 S-->
					  <div class="nb_page J_TwoPage" style="display: none;">
						  <div class="nb_page_hd">你期待的行程安排及人均预算是？</div>


						  <div class="flex_wrap nb_page_item nb_page_route nb_mrb20">
							  <div class="flex_item nb_page_label"><span>*</span>行程安排：</div>
							  <div class="flex_item nb_page_field" id="J_NBTwoType">
								  <div class="nb_form_checkbox">
									  <ul class="flex_wrap"></ul>
								  </div>
								  <div class="nb_form_checkbox">
                                      <ul class="flex_wrap">
                                          <li class="flex_item" data-arrange="compact"><a href="javascript:;">紧凑点，多玩景点</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-arrange="moderate"><a href="javascript:;">适中，不要太赶</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-arrange="loose"><a href="javascript:;">宽松，休闲为主</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-arrange="unsure"><a href="javascript:;">不确定</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                      </ul>
                                  </div>
                              </div>
						  </div>



						  <div class="flex_wrap nb_page_item nb_page_amount nb_mrb20">
							  <div class="flex_item nb_page_label"><span>*</span>出游人数：</div>
							  <div class="flex_item nb_page_field nb_form_field_number" id="J_NBTwoAdult">
                                  <div class="flex_wrap nb_form_number">
                                      <div class="flex_item nb_form_btn nb_form_minus"><a class="J_Minus" href="javascript:;"><i class="nb_icon"></i></a></div>
                                      <div class="flex_item nb_form_input"><input id="J_Adult" class="J_Number" type="text"></div>
                                      <div class="flex_item nb_form_btn nb_form_plus"><a class="J_Plus" href="javascript:;"><i class="nb_icon"></i></a></div>
                                  </div>
                              </div>
							  <div class="flex_item nb_page_txt">个成人</div>

							  <div class="flex_item nb_page_field nb_form_field_number" id="J_NBTwoChild">
                                  <div class="flex_wrap nb_form_number">
                                      <div class="flex_item nb_form_btn nb_form_minus"><a class="J_Minus" href="javascript:;"><i class="nb_icon"></i></a></div>
                                      <div class="flex_item nb_form_input"><input id="J_Child" class="J_Number" type="text"></div>
                                      <div class="flex_item nb_form_btn nb_form_plus"><a class="J_Plus" href="javascript:;"><i class="nb_icon"></i></a></div>
                                  </div>
                              </div>
							  <div class="flex_item nb_page_txt">个儿童</div>
							  <div class="flex_item nb_page_txt">
                                  <a href="javascript:;" class="J_ChildStandard" data-toggle="tooltip" data-placement="bottom"
                                     title="年龄2-12周岁（不含），不占床，不含早餐，其他服务同成人，除行程中规定不接待的项目">儿童标准</a>
                              </div>
							  <p class="nb_more_ten J_MoreTen" style="display: block;">人数不足10人时，定制价格相对较高，建议您可选择网站线路或进行电话(4007-999-999)咨询.</p>
							  <p class="nb_more_ten J_MTten" style="display: none;">超过10人时，建议您选择公司定制。</p>
						  </div>



						  <div class="flex_wrap nb_page_item nb_page_budget nb_mrb20">
							  <div class="flex_item nb_page_label"><span>*</span>人均预算：</div>
							  <div class="flex_item nb_page_field" id="J_NBTwoMethod">
                                  <div class="nb_form_checkbox">
                                      <ul class="flex_wrap">
                                          <li class="flex_item" data-minPrice="1" data-maxPrice="1000"><a href="javascript:;">1-1000</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-minPrice="1000" data-maxPrice="3000"><a href="javascript:;">1000-3000</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-minPrice="3000" data-maxPrice="5000"><a href="javascript:;">3000-5000</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-minPrice="5000" data-maxPrice="8000"><a href="javascript:;">5000-8000</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-minPrice="8000" data-maxPrice="15000"><a href="javascript:;">8000-15000</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-minPrice="15000" data-maxPrice="20000"><a href="javascript:;">15000-20000</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-minPrice="20000" data-maxPrice=""><a href="javascript:;">20000以上</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                          <li class="flex_item" data-minPrice="" data-maxPrice=""><a href="javascript:;">不确定</a><i class="nb_icon nb_form_checkbox_icon"></i></li>
                                      </ul>
                                  </div>
                              </div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_page_item_term nb_hide J_PTips">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field">
								  <p class="nb_page_tips"></p>
							  </div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_page_item_term">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field nb_mrt20">
								  <input type="button" data-page="J_OnePage" value="上一步" class="J_NBPrev nb_p_prevbtn" name="nbSubmittw">
								  <input type="button" data-page="J_FivePage" value="下一步" class="J_NBNext nb_p_prevbtn nb_p_nextbtn nb_unable" name="nbSubmittwo" id="J_BtnTwoNext" disabled>
							  </div>
						  </div>

					  </div>
					  <!--page 2 E-->
					  <!--page 3 S-->
					  <div class="nb_page J_ThreePage" style="display: none;">
						  <div class="nb_page_hd">你想要住的酒店和想要去的景点是？</div>

						  <div class="flex_wrap nb_page_item nb_page_live nb_mrb30">
							  <div class="flex_item nb_page_label"><span>*</span>住宿：</div>
							  <div class="flex_item nb_page_field" id="J_NBThreeType"><div class="nb_form_checkbox"><ul class="flex_wrap"><li class="flex_item nb_form_checkbox_3"><a href="javascript:;"><img src="http://images.tuniucdn.com/img/201504231415/helpcow/room1.jpg"><em>奢华型（5星）</em></a><i class="nb_icon nb_form_checkbox_icon"></i><i class="nb_icon nb_form_checkbox_box"></i></li><li class="flex_item nb_form_checkbox_3"><a href="javascript:;"><img src="http://images.tuniucdn.com/img/201504231415/helpcow/room2.jpg"><em>舒适型（3-4星）</em></a><i class="nb_icon nb_form_checkbox_icon"></i><i class="nb_icon nb_form_checkbox_box"></i></li><li class="flex_item nb_form_checkbox_3"><a href="javascript:;"><img src="http://images.tuniucdn.com/img/201504231415/helpcow/room3.jpg"><em>经济型（0-2星）</em></a><i class="nb_icon nb_form_checkbox_icon"></i><i class="nb_icon nb_form_checkbox_box"></i></li><li class="flex_item nb_form_checkbox_4"><a href="javascript:;">不确定</a><i class="nb_icon nb_form_checkbox_icon"></i><i class="nb_icon nb_form_checkbox_box"></i></li></ul></div></div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_mrb0">
							  <div class="flex_item nb_page_label">景点：</div>
							  <div class="flex_item nb_page_field nb_relative J_Spot">
								  <div class="flex_item nb_page_item J_AddSpot">
									  <div class="nb_form_input">
										  <input id="J_NBThreeDest" type="text" class="nb_grey" placeholder="请输入您想去的景点" autocomplete="off">
									  </div>
								  </div>
								  <div class="nb_cover J_NbCover"></div>
							  </div>
						  </div>
						  <div class="flex_wrap nb_page_item nb_mrb0">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field ">
								  <div class="nb_page_item nb_page_item_term">
									  <div class="flex_item nb_page_field">
										  <div class="flex_item nb_form_btn nb_form_checkbox_2 ">
											  <a href="javascript:;" class="J_AdjustSpot"><i class="nb_icon"></i></a>
										  </div>
										  <div class="flex_item nb_page_item_term_det">还没想好去哪些景点（勾选此项则以上所选择的景点都无效哦）</div>
									  </div>
								  </div>
							  </div>
						  </div>

						  <div class="flex_wrap nb_page_item nb_page_item_term nb_hide J_PTips">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field">
								  <p class="nb_page_tips">错误提示</p>
							  </div>
						  </div>
						  <div class="flex_wrap nb_page_item nb_page_item_term nb_mrt10">
							  <div class="flex_item nb_page_label"></div>
							  <div class="flex_item nb_page_field">
								  <input type="button" value="上一步" class="J_NBPrev nb_p_prevbtn" name="nbSubmitthr">
								  <input type="button" value="下一步" class="J_NBNext nb_p_prevbtn nb_p_nextbtn nb_unable" name="nbSubmitthree" disabled="">
							  </div>
						  </div>

					  </div>
					  <!--page 3 E-->
					  <!--page 4 S-->
					  <div class="nb_page J_FourPage" style="display: none;">

						  <!---------->
						  <div class="nb_page_hd">是否还有其他要补充的问题？</div>
						  <div class="nb_page_field">
							  <div class="nb_remarks">
								  <div class="nb_rem_box">
									  <label class="nb_label">补充备注:</label>
									  <textarea class="nb_rem_area" name="marksText" id="FourPage" maxlength="">亲还有什么想要补充的吗？写的越详细，越能够获得靠谱的定制（推荐）哦！</textarea>
									  <div class="nb_rem_tips">
										  <span class="J_RemNum">0</span>/150
									  </div>
								  </div>
								  <div class="nb_p_submit">
									  <label class="nb_label"></label>
									  <input type="button" value="上一步" class="J_NBPrev nb_p_prevbtn" name="nbSubmitfor">
									  <input type="button" value="下一步" class="J_NBNext nb_p_prevbtn nb_p_nextbtn" name="nbSubmitfour">
								  </div>
							  </div>
						  </div>
						  <!---------->

					  </div>
					  <!--page 4 E-->
					  <!--page 5 S-->
					  <div class="nb_page nb_pr30 J_PFV J_FivePage" style="display: none;">

						  <!---------->
						  <div class="nb_msg">
							  <span class="tn_fontface nb_msg_icon"></span>
							  <div class="nb_msg_hd">
								  <p class="nb_text">所有问题都已回答完毕，请留下你的联系方式</p>
								  <p class="nb_intro">留下你的联系方式，帮妹将会第一时间和你联系哦！</p>
							  </div>

						  </div>
						  <div class="nb_person_msg">
							  <div class="nb_p_item">
								  <label class="nb_label"><span class="nb_label_xing">*</span><span class="nb_label_name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span></label>
								  <input type="text" value="" class="nb_p_input J_FvName" maxlength="10" name="nbName" id="J_Name">
							  </div>
							  <div class="J_NBPhone nb_p_item">
								  <label class="nb_label"><span class="nb_label_xing">*</span><span class="nb_label_name">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</span></label>
								  <input type="text" value="" class="nb_p_input J_FvPhone" maxlength="11" name="nbPhone" id="J_Phone">
								  <p class="nb_no_reg">提交需求后系统将自动为您注册会员，请注意查收短信提醒。</p>
							  </div>
							  <div class="nb_p_item">
								  <label class="nb_label"><span class="nb_label_xing">*</span><span class="nb_label_name">短信验证码：</span></label>
								  <input type="text" value="" class="nb_p_btn J_FvSms" name="nbSmsVefy" maxlength="6" disabled="disabled">
								  <input type="button" class="nb_p_smsbtn nb_rem_disabled J_FvGetSms" value="获取" disabled="disabled">
								  <p class="nb_p_time nb_hide J_FvTimeP"><span class="J_FvTime">59</span>s后重新获取</p>
							  </div>
							  <div class="nb_p_item">
								  <label class="nb_label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</label>
								  <input type="text" value="" class="nb_p_input J_FvEmail" name="nbSmsVefy" maxlength="64" id="J_Email">
							  </div>
							  <div class="nb_p_tips J_PTips">
								  <label class="nb_label"></label>
								  <p class="nb_page_tips">错误提示</p>
							  </div>
							  <div class="nb_p_submit">
								  <label class="nb_label"></label>
								  <input type="button" data-page="J_TwoPage" value="上一步" class="J_NBPrev nb_p_prevbtn" name="nbSubmitfiv">
								  <input type="button" data-page="J_EndPage" value="提 交" class="J_NBNext nb_p_prevbtn nb_p_nextbtn nb_unable" name="nbSubmitfive" id="J_BtnFiveNext" disabled>
							  </div>
						  </div>
						  <!---------->

					  </div>
					  <!--page 5 E-->
				  </div>
				  <!--牛帮问题页 E-->
			  </div>
			  <!--牛帮问题区 E-->
			  <div class="nb_menu" id="J_NBMenu">
				  <i class="nb_icon nb_menu_arrow"></i>
				  <div class="nb_menu_bg"></div>
				  <div class="nb_menu_box">
					  <div class="nb_menu_hd">我的需求单</div>
					  <div class="nb_menu_list" id="J_NBMenuList">
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">出游类型</div>
                              <div class="flex_wrap nb_menu_item_con">
                                  <!--<div class="flex_item nb_menu_item_res">公司定制(大于10人)</div>-->
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">从哪出发</div>
                              <div class="flex_wrap nb_menu_item_con">
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">到哪儿玩</div>
                              <div class="flex_wrap nb_menu_item_con">
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">玩多久</div>
                              <div class="flex_wrap nb_menu_item_con">
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">啥时候出发</div>
                              <div class="flex_wrap nb_menu_item_con">
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">行程安排</div>
                              <div class="flex_wrap nb_menu_item_con">
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">出游人数</div>
                              <div class="flex_wrap nb_menu_item_con">
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">人均预算</div>
                              <div class="flex_wrap nb_menu_item_con">
                              </div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">姓名</div>
                              <div class="flex_wrap nb_menu_item_con"></div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">手机号</div>
                              <div class="flex_wrap nb_menu_item_con"></div>
                          </div>
                          <div class="nb_menu_item clearfix">
                              <div class="nb_menu_item_tit">邮箱</div>
                              <div class="flex_wrap nb_menu_item_con"></div>
                          </div>
                      </div>
				  </div>
			  </div>
		  </div>
		  <!-- 定制需求单 结束 -->
	</div>
</div>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/customRequire/fill.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
</body>
</html>
