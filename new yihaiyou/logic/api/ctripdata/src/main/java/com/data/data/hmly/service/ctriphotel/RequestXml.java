package com.data.data.hmly.service.ctriphotel;

/**
 * Created by vacuity on 15/12/3.
 */
public class RequestXml {

    public String AVAIL_REQUEST = "<!--版本信息:2013年8月-->" +
            "<!--可订检查OTA_HotelAvail -->" +
            "<!--接口提供方：携程；调用方：合作方-->" +
            "<Request>" +
            "    <!--AllianceID:分销商ID;SID:站点ID;TimeStamp:响应时间戳（从1970年到现在的秒数）;RequestType:请求接口的类型;Signature:MD5加密串-->" +
            "    <Header AllianceID=\"#AllianceID\" SID=\"#SID\" TimeStamp=\"#TimeStamp\" RequestType=\"OTA_HotelAvail\"" +
            "            Signature=\"#Signature\"/>" +
            "    <HotelRequest>" +
            "        <RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\">" +
            "            <ns:OTA_HotelAvailRQ Version=\"1.0\" TimeStamp=\"2012-04-20T00:00:00.000+08:00\">" +
            "                <!--可订性查询根节点，必填-->" +
            "                <ns:AvailRequestSegments>" +
            "                    <ns:AvailRequestSegment>" +
            "                        <!--酒店可订性查询条件列表，必填-->" +
            "                        <ns:HotelSearchCriteria>" +
            "                            <!--搜索条件，必填-->" +
            "                            <ns:Criterion>" +
            "                                <!--酒店信息-->" +
            "                                <!-- HotelCode属性：酒店代码；string类型；必填 -->" +
            "                                <ns:HotelRef HotelCode=\"#HotelCode\"/>" +
            "                                <!--入住和离店日期-->" +
            "                                <!-- Start属性：入住日期；DateTime类型；必填 -->" +
            "                                <!-- End属性：离店日期；DateTime类型；必填 -->" +
            "                                <!-- <ns:StayDateRange Start=\"2013-06-27T13:00:00.000+08:00\" End=\"2013-06-28T15:00:00.000+08:00\" /> -->" +
            "                                <ns:StayDateRange Start=\"#Start\" End=\"#End\"/>" +
            "                                <!--指定价格计划列表，可空-->" +
            "                                <ns:RatePlanCandidates>" +
            "                                    <!--指定价格计划-->" +
            "                                    <!-- RatePlanCode属性：价格计划代码；string类型；必填 -->" +
            "                                    <ns:RatePlanCandidate RatePlanCode=\"#RatePlanCode\"/>" +
            "                                </ns:RatePlanCandidates>" +
            "                                <!--指定房型列表，最多1个-->" +
            "                                <ns:RoomStayCandidates>" +
            "                                    <!--指定房型-->" +
            "                                    <!-- Quantity属性：将要预订的房间数量；int类型；必填 -->" +
            "                                    <ns:RoomStayCandidate Quantity=\"#Quantity\">" +
            "                                        <!--客人数量信息-->" +
            "                                        <!-- IsPerRoom属性：客人数量是否对应每间房，False 表示所有房间加起来一共住这么多客人；bool类型；可空 -->" +
            "                                        <ns:GuestCounts IsPerRoom=\"false\">" +
            "                                            <!--客人数量-->" +
            "                                            <!-- Count属性：客人数量；int类型；必填 -->" +
            "                                            <ns:GuestCount Count=\"#Count\"/>" +
            "                                        </ns:GuestCounts>" +
            "                                    </ns:RoomStayCandidate>" +
            "                                </ns:RoomStayCandidates>" +
            "                                <!--附加信息-->" +
            "                                <ns:TPA_Extensions>" +
            "                                    <!--入住人最晚到店时间，有可能最晚到店日期为第二天凌晨，格式为 yyyy-MM-dd hh:mm:ss，必填 -->" +
            "                                    <!--<ns:LateArrivalTime>2013-06-27T15:00:00.000+08:00</ns:LateArrivalTime>  -->" +
            "                                    <ns:LateArrivalTime>#LateArrivalTime</ns:LateArrivalTime>" +
            "                                </ns:TPA_Extensions>" +
            "                            </ns:Criterion>" +
            "                        </ns:HotelSearchCriteria>" +
            "                    </ns:AvailRequestSegment>" +
            "                </ns:AvailRequestSegments>" +
            "            </ns:OTA_HotelAvailRQ>" +
            "        </RequestBody>" +
            "    </HotelRequest>" +
            "</Request>";


    public String CUSTOMER_NAME =
            "<ns:PersonName>" +
            "    <ns:Surname>#SURNAME</ns:Surname>" +
            "</ns:PersonName>";

    public String SPECIAL_REQUEST =
            "<ns:SpecialRequest>" +
            "    <ns:Text>#SPECIAL_REQUEST</ns:Text>" +
            "</ns:SpecialRequest>";

    public String HOTEL_ORDER = "<?xml version=\"1.0\"?>\n" +
            "<Request>\n" +
            "    <Header AllianceID=\"#AllianceID\" SID=\"#SID\" TimeStamp=\"#TimeStamp\" RequestType=\"OTA_HotelRes\"\n" +
            "            Signature=\"#Signature\"/>\n" +
            "\n" +
            "    <HotelRequest>\n" +
            "        <RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "            <ns:OTA_HotelResRQ TimeStamp=\"2012-04-21T00:00:00.000+08:00\" Version=\"2.0\" PrimaryLangID=\"zh\" RatePlanCategory=\"501\" >\n" +
            "                <ns:UniqueID ID=\"100000\" Type=\"504\"/>\n" +
            "                <ns:UniqueID Type=\"28\" ID=\"#AllianceID\"/>\n" +
            "                <ns:UniqueID Type=\"503\" ID=\"#SID\"/>\n" +
            "                <ns:UniqueID Type=\"1\" ID=\"#UNIQUEID\"/>\n" +
            "                <ns:HotelReservations>\n" +
            "                    <ns:HotelReservation>\n" +
            "                        <ns:RoomStays>\n" +
            "                            <ns:RoomStay>\n" +
            "                                <ns:RoomTypes>\n" +
            "                                    <ns:RoomType NumberOfUnits=\"#NUMBER\"/>\n" +
            "                                </ns:RoomTypes>\n" +
            "                                <ns:RatePlans>\n" +
            "                                    <ns:RatePlan RatePlanCode=\"#RATE_PLAN_CODE\"/>\n" +
            "                                </ns:RatePlans>\n" +
            "                                <ns:BasicPropertyInfo HotelCode=\"#HOTEL_CODE\"/>\n" +
            "                            </ns:RoomStay>\n" +
            "                        </ns:RoomStays>\n" +
            "                        <ns:ResGuests>\n" +
            "                            <ns:ResGuest ArrivalTime=\"#ARRIVAL_TIME\">\n" +
            "                                <ns:Profiles>\n" +
            "                                    <ns:ProfileInfo>\n" +
            "                                        <ns:Profile>\n" +
            "                                            <ns:Customer>\n" +
            "                                                #CUSTOMERS\n" +
            "\n" +
            "                                                <!--sms\t    confirm contact person by short message\t*\t\t手机短消息确认-->\n" +
            "                                                <!--email\tconfirm contact person by email\t*\t\t\t\t电邮确认-->\n" +
            "                                                <!--tel\t    confirm contact person by  telephone/mobile\t*\t电话确认-->\n" +
            "                                                <!--fax\t    confirm contact person by  fax\t*\t\t\t\t传真确认-->\n" +
            "                                                <!--non\t    no confirm needed\t*\t\t\t\t\t\t\t无需确认-->\n" +
            "                                                <ns:ContactPerson ContactType=\"SMS\">\n" +
            "                                                    <ns:PersonName>\n" +
            "                                                        <ns:Surname>#SURNAME</ns:Surname>\n" +
            "                                                    </ns:PersonName>\n" +
            "                                                    <!--1\tVoice-->\n" +
            "                                                    <!--2\tData-->\n" +
            "                                                    <!--3\tFax-->\n" +
            "                                                    <!--4\tPager-->\n" +
            "                                                    <!--5\tMobile-->\n" +
            "                                                    <!--8\tVoice over IP-->\n" +
            "                                                    <ns:Telephone PhoneNumber=\"#PHONE_NUMBER\" PhoneTechType=\"1\"/>\n" +
            "                                                    <ns:Email>#EMAIL</ns:Email>\n" +
            "                                                </ns:ContactPerson>\n" +
            "                                            </ns:Customer>\n" +
            "                                        </ns:Profile>\n" +
            "                                    </ns:ProfileInfo>\n" +
            "                                </ns:Profiles>\n" +
            "                                <ns:TPA_Extensions>\n" +
            "                                    <ns:LateArrivalTime>#LATE_ARRIVAL_TIME</ns:LateArrivalTime>\n" +
            "                                </ns:TPA_Extensions>\n" +
            "                            </ns:ResGuest>\n" +
            "                        </ns:ResGuests>\n" +
            "                        <ns:ResGlobalInfo>\n" +
            "                            <ns:GuestCounts IsPerRoom=\"false\">\n" +
            "                                <ns:GuestCount Count=\"#GUEST_COUNT\"/>\n" +
            "                            </ns:GuestCounts>\n" +
            "                            <ns:TimeSpan Start=\"#START\" End=\"#END\"/>\n" +
            "                            <ns:SpecialRequests>\n" +
            "\n" +
            "\n" +
            "                                #SPECIAL_REQUESTS\n" +
            "\n" +
            "\n" +
            "                            </ns:SpecialRequests>\n" +
            "                            <!--担保-->\n" +
            "                            <!--<ns:DepositPayments>-->\n" +
            "                                <!--&lt;!&ndash;Deposit\t押金担保&ndash;&gt;-->\n" +
            "                                <!--&lt;!&ndash;CCDCVoucher\t信用卡担保&ndash;&gt;-->\n" +
            "                                <!--&lt;!&ndash;PrePay\t预付担保&ndash;&gt;-->\n" +
            "                                <!--<ns:GuaranteePayment GuaranteeType=\"Deposit\">-->\n" +
            "                                    <!--<ns:AcceptedPayments>-->\n" +
            "                                        <!--<ns:AcceptedPayment/>-->\n" +
            "                                    <!--</ns:AcceptedPayments>-->\n" +
            "                                    <!--<ns:AmountPercent Amount=\"#PAYMENT_AMOUNT\" CurrencyCode=\"CNY\"/>-->\n" +
            "                                <!--</ns:GuaranteePayment>-->\n" +
            "                            <!--</ns:DepositPayments>-->\n" +
            "                            <!--税前订单总价；Money类型；必填-->\n" +
            "                            <ns:Total AmountBeforeTax=\"#AMOUNT_BEFORE_TAX\" CurrencyCode=\"CNY\"/>\n" +
            "\n" +
            "                            <!--取消制度，定义最晚取消时间和取消金额。取消罚金数量等于担保金数量，可空-->\n" +
            "                            <!--<ns:CancelPenalties>-->\n" +
            "                                <!--&lt;!&ndash;酒店损失定义最晚取消时间和取消金额。取消罚金数量等于担保金数量&ndash;&gt;-->\n" +
            "                                <!--&lt;!&ndash; Start属性：开始时间；dateTime类型；必填 &ndash;&gt;-->\n" +
            "                                <!--&lt;!&ndash; End属性：结束时间；dateTime类型；必填 &ndash;&gt;-->\n" +
            "                                <!--&lt;!&ndash; Start属性：开始时间；End属性：结束时间，表示在这个时间段取消是需要扣除罚金，start表示了最迟的取消时间，在这个时间前取消不需要扣除罚金&ndash;&gt;-->\n" +
            "                                <!--<ns:CancelPenalty Start=\"#CANCEL_START\" End=\"#CANCEL_END\">-->\n" +
            "                                    <!--&lt;!&ndash;取消罚金&ndash;&gt;-->\n" +
            "                                    <!--&lt;!&ndash; Amount属性：罚金；Money类型；必填 &ndash;&gt;-->\n" +
            "                                    <!--&lt;!&ndash; CurrencyCode属性：货币单位；Money类型；可空 &ndash;&gt;-->\n" +
            "                                    <!--<ns:AmountPercent Amount=\"#CANCEL_AMOUNT\" CurrencyCode=\"CNY\"/>-->\n" +
            "                                <!--</ns:CancelPenalty>-->\n" +
            "                            <!--</ns:CancelPenalties>-->\n" +
            "\n" +
            "                        </ns:ResGlobalInfo>\n" +
            "                    </ns:HotelReservation>\n" +
            "                </ns:HotelReservations>\n" +
            "            </ns:OTA_HotelResRQ>\n" +
            "        </RequestBody>\n" +
            "    </HotelRequest>\n" +
            "</Request>\n" +
            "\n";
}
