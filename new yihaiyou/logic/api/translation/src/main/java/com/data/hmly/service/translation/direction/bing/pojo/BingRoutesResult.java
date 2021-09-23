package com.data.hmly.service.translation.direction.bing.pojo;

import java.util.List;

/**
 * Created by Sane on 16/4/26.
 */
public class BingRoutesResult {

    /**
     * authenticationResultCode : ValidCredentials
     * brandLogoUri : http://dev.virtualearth.net/Branding/logo_powered_by.png
     * copyright : Copyright © 2016 Microsoft and its suppliers. All rights reserved. This API cannot be accessed and the content and any results may not be used, reproduced or transmitted in any manner without express written permission from Microsoft Corporation.
     * resourceSets : [{"estimatedTotal":1,"resources":[{"__type":"Route:http://schemas.microsoft.com/search/local/ws/rest/v1","bbox":[21.903482,120.691289,22.291871,120.893362],"id":"v67,h-1345094408,i0,a0,cen-US,dAAAAAAAAAAA1,y0,s1,m1,o1,t4,wdEfFNaVBNkAtZ5qKpzleQA2~BAdFQzkxPKFOAADgAZ_HAj8C0~MjY1~~~~v10,wYgtmkXfpNUBaP84dATdeQA2~BAdFQzmRtaROAADgAeXHcD8C0~MjYgLyBLZW5nIE5laSBSZC41~~~~v10,k1","distanceUnit":"Kilometer","durationUnit":"Second","routeLegs":[{"actualEnd":{"type":"Point","coordinates":[21.91126,120.855322]},"actualStart":{"type":"Point","coordinates":[22.254728,120.893362]},"alternateVias":[],"cost":0,"description":"26, 9","itineraryItems":[{"compassDirection":"north","details":[{"compassDegrees":344,"endPathIndices":[13],"locationCodes":["D10+04829"],"maneuverType":"DepartStart","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[0]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"DepartStart","text":"Depart 26 toward Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.254728,120.893362]},"sideOfStreet":"Unknown","tollZone":"","towardsRoadName":"Nan Tian Rd.","transitTerminus":"","travelDistance":1.925,"travelDuration":209,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":358,"endPathIndices":[15],"maneuverType":"KeepRight","mode":"Driving","names":["Nan Tian Rd."],"roadType":"Street","startPathIndices":[13]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepRight","text":"Keep right onto Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.271401,120.891071]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.231,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":341,"endPathIndices":[17],"locationCodes":["D10+04829"],"maneuverType":"KeepStraight","mode":"Driving","names":["Nan Tian Rd."],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[15]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepStraight","text":"Keep straight onto 26 / Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.273412,120.890669]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.11,"travelDuration":13,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":353,"endPathIndices":[38],"locationCodes":["D10+04829","D10P04829"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[17]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26"},"maneuverPoint":{"type":"Point","coordinates":[22.274373,120.890433]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":2.825,"travelDuration":630,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":218,"endPathIndices":[417],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Nan Hui Highway"],"roadShieldRequestParameters":{"bucket":524942,"shields":[{"labels":["9"],"roadShieldType":3}]},"roadType":"Highway","startPathIndices":[38]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 9 / Nan Hui Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.291871,120.881179]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":29.347,"travelDuration":2375,"travelMode":"Driving","warnings":[{"origin":"22.29126,120.88053","severity":"Minor","text":"Minor Congestion","to":"22.291871,120.881179","warningType":"TrafficFlow"}]},{"compassDirection":"south","details":[{"compassDegrees":180,"endPathIndices":[420],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ba Shi Lane"],"roadType":"MajorRoad","startPathIndices":[417]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto Ba Shi Lane"},"maneuverPoint":{"type":"Point","coordinates":[22.206738,120.706519]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.122,"travelDuration":54,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":225,"endPathIndices":[424],"locationCodes":["D10+04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Lane Ba Shih"],"roadType":"MajorRoad","startPathIndices":[420]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Lane Ba Shih"},"maneuverPoint":{"type":"Point","coordinates":[22.205778,120.705961]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.463,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"west","details":[{"compassDegrees":284,"endPathIndices":[434],"locationCodes":["D10+04896","D10P04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Jiu Zhuang Rd."],"roadType":"MajorRoad","startPathIndices":[424]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Jiu Zhuang Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.204383,120.702292]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":1.561,"travelDuration":156,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":158,"endPathIndices":[521],"locationCodes":["D10P04822","D10+08959","D10+09021","D10+04823","D10+09011","D10+08960","D10+08961","D10+08962","D10+09006","D10+08963"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ping E Highway"],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[434]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 26 / Ping E Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.194749,120.691981]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":21.793,"travelDuration":1594,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":188,"endPathIndices":[628],"locationCodes":["D10+08964","D10+08965","D10+04824","D10+08966","D10P08966","D10+08967","D10P08967","D10+04825","D10+04826","D10+08968"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["Heng Gong Rd."],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[521]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26 / Heng Gong Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.015738,120.744081]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":20.571,"travelDuration":1575,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":356,"endPathIndices":[628],"locationCodes":["D10+08968"],"maneuverType":"ArriveFinish","mode":"Driving","names":["Keng Nei Rd."],"roadShieldRequestParameters":{"bucket":524951,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[628]}],"exit":"","hints":[{"hintType":"NextIntersection","text":"If you reach Lane 55, Keng Nei Rd., you've gone too far"}],"iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"ArriveFinish","text":"Arrive at 26 / Keng Nei Rd. on the right"},"maneuverPoint":{"type":"Point","coordinates":[21.91126,120.855322]},"sideOfStreet":"Right","tollZone":"","transitTerminus":"","travelDistance":0,"travelDuration":0,"travelMode":"Driving"}],"routeRegion":"WR","routeSubLegs":[{"endWaypoint":{"type":"Point","coordinates":[21.91126,120.855322],"description":"26 / Keng Nei Rd.","isVia":false,"locationIdentifier":"4|7|69|67|57|145|181|164|78|0|0|224|1|229|199|112|63|2|21.91126,120.855322","routePathIndex":628},"startWaypoint":{"type":"Point","coordinates":[22.254728,120.893362],"description":"26","isVia":false,"locationIdentifier":"4|7|69|67|57|49|60|161|78|0|0|224|1|159|199|2|63|2|22.254728,120.893362","routePathIndex":0},"travelDistance":78.948,"travelDuration":6663}],"travelDistance":78.948,"travelDuration":6663}],"trafficCongestion":"Mild","trafficDataUsed":"None","travelDistance":78.948,"travelDuration":6663,"travelDurationTraffic":7604}]}]
     * statusCode : 200
     * statusDescription : OK
     * traceId : 0710c94df7bf46308b5dd530b307d0ef|BN20130632|1.0.0.0|BN20000729, BN20130832
     */

    private String authenticationResultCode;
    private String brandLogoUri;
    private String copyright;
    private int statusCode;
    private String statusDescription;
    private String traceId;
    /**
     * estimatedTotal : 1
     * resources : [{"__type":"Route:http://schemas.microsoft.com/search/local/ws/rest/v1","bbox":[21.903482,120.691289,22.291871,120.893362],"id":"v67,h-1345094408,i0,a0,cen-US,dAAAAAAAAAAA1,y0,s1,m1,o1,t4,wdEfFNaVBNkAtZ5qKpzleQA2~BAdFQzkxPKFOAADgAZ_HAj8C0~MjY1~~~~v10,wYgtmkXfpNUBaP84dATdeQA2~BAdFQzmRtaROAADgAeXHcD8C0~MjYgLyBLZW5nIE5laSBSZC41~~~~v10,k1","distanceUnit":"Kilometer","durationUnit":"Second","routeLegs":[{"actualEnd":{"type":"Point","coordinates":[21.91126,120.855322]},"actualStart":{"type":"Point","coordinates":[22.254728,120.893362]},"alternateVias":[],"cost":0,"description":"26, 9","itineraryItems":[{"compassDirection":"north","details":[{"compassDegrees":344,"endPathIndices":[13],"locationCodes":["D10+04829"],"maneuverType":"DepartStart","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[0]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"DepartStart","text":"Depart 26 toward Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.254728,120.893362]},"sideOfStreet":"Unknown","tollZone":"","towardsRoadName":"Nan Tian Rd.","transitTerminus":"","travelDistance":1.925,"travelDuration":209,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":358,"endPathIndices":[15],"maneuverType":"KeepRight","mode":"Driving","names":["Nan Tian Rd."],"roadType":"Street","startPathIndices":[13]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepRight","text":"Keep right onto Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.271401,120.891071]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.231,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":341,"endPathIndices":[17],"locationCodes":["D10+04829"],"maneuverType":"KeepStraight","mode":"Driving","names":["Nan Tian Rd."],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[15]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepStraight","text":"Keep straight onto 26 / Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.273412,120.890669]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.11,"travelDuration":13,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":353,"endPathIndices":[38],"locationCodes":["D10+04829","D10P04829"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[17]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26"},"maneuverPoint":{"type":"Point","coordinates":[22.274373,120.890433]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":2.825,"travelDuration":630,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":218,"endPathIndices":[417],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Nan Hui Highway"],"roadShieldRequestParameters":{"bucket":524942,"shields":[{"labels":["9"],"roadShieldType":3}]},"roadType":"Highway","startPathIndices":[38]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 9 / Nan Hui Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.291871,120.881179]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":29.347,"travelDuration":2375,"travelMode":"Driving","warnings":[{"origin":"22.29126,120.88053","severity":"Minor","text":"Minor Congestion","to":"22.291871,120.881179","warningType":"TrafficFlow"}]},{"compassDirection":"south","details":[{"compassDegrees":180,"endPathIndices":[420],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ba Shi Lane"],"roadType":"MajorRoad","startPathIndices":[417]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto Ba Shi Lane"},"maneuverPoint":{"type":"Point","coordinates":[22.206738,120.706519]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.122,"travelDuration":54,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":225,"endPathIndices":[424],"locationCodes":["D10+04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Lane Ba Shih"],"roadType":"MajorRoad","startPathIndices":[420]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Lane Ba Shih"},"maneuverPoint":{"type":"Point","coordinates":[22.205778,120.705961]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.463,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"west","details":[{"compassDegrees":284,"endPathIndices":[434],"locationCodes":["D10+04896","D10P04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Jiu Zhuang Rd."],"roadType":"MajorRoad","startPathIndices":[424]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Jiu Zhuang Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.204383,120.702292]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":1.561,"travelDuration":156,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":158,"endPathIndices":[521],"locationCodes":["D10P04822","D10+08959","D10+09021","D10+04823","D10+09011","D10+08960","D10+08961","D10+08962","D10+09006","D10+08963"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ping E Highway"],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[434]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 26 / Ping E Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.194749,120.691981]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":21.793,"travelDuration":1594,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":188,"endPathIndices":[628],"locationCodes":["D10+08964","D10+08965","D10+04824","D10+08966","D10P08966","D10+08967","D10P08967","D10+04825","D10+04826","D10+08968"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["Heng Gong Rd."],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[521]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26 / Heng Gong Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.015738,120.744081]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":20.571,"travelDuration":1575,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":356,"endPathIndices":[628],"locationCodes":["D10+08968"],"maneuverType":"ArriveFinish","mode":"Driving","names":["Keng Nei Rd."],"roadShieldRequestParameters":{"bucket":524951,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[628]}],"exit":"","hints":[{"hintType":"NextIntersection","text":"If you reach Lane 55, Keng Nei Rd., you've gone too far"}],"iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"ArriveFinish","text":"Arrive at 26 / Keng Nei Rd. on the right"},"maneuverPoint":{"type":"Point","coordinates":[21.91126,120.855322]},"sideOfStreet":"Right","tollZone":"","transitTerminus":"","travelDistance":0,"travelDuration":0,"travelMode":"Driving"}],"routeRegion":"WR","routeSubLegs":[{"endWaypoint":{"type":"Point","coordinates":[21.91126,120.855322],"description":"26 / Keng Nei Rd.","isVia":false,"locationIdentifier":"4|7|69|67|57|145|181|164|78|0|0|224|1|229|199|112|63|2|21.91126,120.855322","routePathIndex":628},"startWaypoint":{"type":"Point","coordinates":[22.254728,120.893362],"description":"26","isVia":false,"locationIdentifier":"4|7|69|67|57|49|60|161|78|0|0|224|1|159|199|2|63|2|22.254728,120.893362","routePathIndex":0},"travelDistance":78.948,"travelDuration":6663}],"travelDistance":78.948,"travelDuration":6663}],"trafficCongestion":"Mild","trafficDataUsed":"None","travelDistance":78.948,"travelDuration":6663,"travelDurationTraffic":7604}]
     */

    private List<ResourceSetsEntity> resourceSets;

    public String getAuthenticationResultCode() {
        return authenticationResultCode;
    }

    public void setAuthenticationResultCode(String authenticationResultCode) {
        this.authenticationResultCode = authenticationResultCode;
    }

    public String getBrandLogoUri() {
        return brandLogoUri;
    }

    public void setBrandLogoUri(String brandLogoUri) {
        this.brandLogoUri = brandLogoUri;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public List<ResourceSetsEntity> getResourceSets() {
        return resourceSets;
    }

    public void setResourceSets(List<ResourceSetsEntity> resourceSets) {
        this.resourceSets = resourceSets;
    }

    public static class ResourceSetsEntity {
        private int estimatedTotal;
        /**
         * __type : Route:http://schemas.microsoft.com/search/local/ws/rest/v1
         * bbox : [21.903482,120.691289,22.291871,120.893362]
         * id : v67,h-1345094408,i0,a0,cen-US,dAAAAAAAAAAA1,y0,s1,m1,o1,t4,wdEfFNaVBNkAtZ5qKpzleQA2~BAdFQzkxPKFOAADgAZ_HAj8C0~MjY1~~~~v10,wYgtmkXfpNUBaP84dATdeQA2~BAdFQzmRtaROAADgAeXHcD8C0~MjYgLyBLZW5nIE5laSBSZC41~~~~v10,k1
         * distanceUnit : Kilometer
         * durationUnit : Second
         * routeLegs : [{"actualEnd":{"type":"Point","coordinates":[21.91126,120.855322]},"actualStart":{"type":"Point","coordinates":[22.254728,120.893362]},"alternateVias":[],"cost":0,"description":"26, 9","itineraryItems":[{"compassDirection":"north","details":[{"compassDegrees":344,"endPathIndices":[13],"locationCodes":["D10+04829"],"maneuverType":"DepartStart","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[0]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"DepartStart","text":"Depart 26 toward Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.254728,120.893362]},"sideOfStreet":"Unknown","tollZone":"","towardsRoadName":"Nan Tian Rd.","transitTerminus":"","travelDistance":1.925,"travelDuration":209,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":358,"endPathIndices":[15],"maneuverType":"KeepRight","mode":"Driving","names":["Nan Tian Rd."],"roadType":"Street","startPathIndices":[13]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepRight","text":"Keep right onto Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.271401,120.891071]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.231,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":341,"endPathIndices":[17],"locationCodes":["D10+04829"],"maneuverType":"KeepStraight","mode":"Driving","names":["Nan Tian Rd."],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[15]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepStraight","text":"Keep straight onto 26 / Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.273412,120.890669]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.11,"travelDuration":13,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":353,"endPathIndices":[38],"locationCodes":["D10+04829","D10P04829"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[17]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26"},"maneuverPoint":{"type":"Point","coordinates":[22.274373,120.890433]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":2.825,"travelDuration":630,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":218,"endPathIndices":[417],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Nan Hui Highway"],"roadShieldRequestParameters":{"bucket":524942,"shields":[{"labels":["9"],"roadShieldType":3}]},"roadType":"Highway","startPathIndices":[38]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 9 / Nan Hui Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.291871,120.881179]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":29.347,"travelDuration":2375,"travelMode":"Driving","warnings":[{"origin":"22.29126,120.88053","severity":"Minor","text":"Minor Congestion","to":"22.291871,120.881179","warningType":"TrafficFlow"}]},{"compassDirection":"south","details":[{"compassDegrees":180,"endPathIndices":[420],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ba Shi Lane"],"roadType":"MajorRoad","startPathIndices":[417]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto Ba Shi Lane"},"maneuverPoint":{"type":"Point","coordinates":[22.206738,120.706519]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.122,"travelDuration":54,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":225,"endPathIndices":[424],"locationCodes":["D10+04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Lane Ba Shih"],"roadType":"MajorRoad","startPathIndices":[420]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Lane Ba Shih"},"maneuverPoint":{"type":"Point","coordinates":[22.205778,120.705961]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.463,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"west","details":[{"compassDegrees":284,"endPathIndices":[434],"locationCodes":["D10+04896","D10P04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Jiu Zhuang Rd."],"roadType":"MajorRoad","startPathIndices":[424]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Jiu Zhuang Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.204383,120.702292]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":1.561,"travelDuration":156,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":158,"endPathIndices":[521],"locationCodes":["D10P04822","D10+08959","D10+09021","D10+04823","D10+09011","D10+08960","D10+08961","D10+08962","D10+09006","D10+08963"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ping E Highway"],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[434]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 26 / Ping E Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.194749,120.691981]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":21.793,"travelDuration":1594,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":188,"endPathIndices":[628],"locationCodes":["D10+08964","D10+08965","D10+04824","D10+08966","D10P08966","D10+08967","D10P08967","D10+04825","D10+04826","D10+08968"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["Heng Gong Rd."],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[521]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26 / Heng Gong Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.015738,120.744081]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":20.571,"travelDuration":1575,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":356,"endPathIndices":[628],"locationCodes":["D10+08968"],"maneuverType":"ArriveFinish","mode":"Driving","names":["Keng Nei Rd."],"roadShieldRequestParameters":{"bucket":524951,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[628]}],"exit":"","hints":[{"hintType":"NextIntersection","text":"If you reach Lane 55, Keng Nei Rd., you've gone too far"}],"iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"ArriveFinish","text":"Arrive at 26 / Keng Nei Rd. on the right"},"maneuverPoint":{"type":"Point","coordinates":[21.91126,120.855322]},"sideOfStreet":"Right","tollZone":"","transitTerminus":"","travelDistance":0,"travelDuration":0,"travelMode":"Driving"}],"routeRegion":"WR","routeSubLegs":[{"endWaypoint":{"type":"Point","coordinates":[21.91126,120.855322],"description":"26 / Keng Nei Rd.","isVia":false,"locationIdentifier":"4|7|69|67|57|145|181|164|78|0|0|224|1|229|199|112|63|2|21.91126,120.855322","routePathIndex":628},"startWaypoint":{"type":"Point","coordinates":[22.254728,120.893362],"description":"26","isVia":false,"locationIdentifier":"4|7|69|67|57|49|60|161|78|0|0|224|1|159|199|2|63|2|22.254728,120.893362","routePathIndex":0},"travelDistance":78.948,"travelDuration":6663}],"travelDistance":78.948,"travelDuration":6663}]
         * trafficCongestion : Mild
         * trafficDataUsed : None
         * travelDistance : 78.948
         * travelDuration : 6663
         * travelDurationTraffic : 7604
         */

        private List<ResourcesEntity> resources;

        public int getEstimatedTotal() {
            return estimatedTotal;
        }

        public void setEstimatedTotal(int estimatedTotal) {
            this.estimatedTotal = estimatedTotal;
        }

        public List<ResourcesEntity> getResources() {
            return resources;
        }

        public void setResources(List<ResourcesEntity> resources) {
            this.resources = resources;
        }

        public static class ResourcesEntity {
            private String __type;
            private String id;
            private String distanceUnit;
            private String durationUnit;
            private String trafficCongestion;
            private String trafficDataUsed;
            private double travelDistance;
            private int travelDuration;
            private int travelDurationTraffic;
            private List<Double> bbox;
            /**
             * actualEnd : {"type":"Point","coordinates":[21.91126,120.855322]}
             * actualStart : {"type":"Point","coordinates":[22.254728,120.893362]}
             * alternateVias : []
             * cost : 0
             * description : 26, 9
             * itineraryItems : [{"compassDirection":"north","details":[{"compassDegrees":344,"endPathIndices":[13],"locationCodes":["D10+04829"],"maneuverType":"DepartStart","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[0]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"DepartStart","text":"Depart 26 toward Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.254728,120.893362]},"sideOfStreet":"Unknown","tollZone":"","towardsRoadName":"Nan Tian Rd.","transitTerminus":"","travelDistance":1.925,"travelDuration":209,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":358,"endPathIndices":[15],"maneuverType":"KeepRight","mode":"Driving","names":["Nan Tian Rd."],"roadType":"Street","startPathIndices":[13]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepRight","text":"Keep right onto Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.271401,120.891071]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.231,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":341,"endPathIndices":[17],"locationCodes":["D10+04829"],"maneuverType":"KeepStraight","mode":"Driving","names":["Nan Tian Rd."],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[15]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepStraight","text":"Keep straight onto 26 / Nan Tian Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.273412,120.890669]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.11,"travelDuration":13,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":353,"endPathIndices":[38],"locationCodes":["D10+04829","D10P04829"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[17]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26"},"maneuverPoint":{"type":"Point","coordinates":[22.274373,120.890433]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":2.825,"travelDuration":630,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":218,"endPathIndices":[417],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Nan Hui Highway"],"roadShieldRequestParameters":{"bucket":524942,"shields":[{"labels":["9"],"roadShieldType":3}]},"roadType":"Highway","startPathIndices":[38]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 9 / Nan Hui Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.291871,120.881179]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":29.347,"travelDuration":2375,"travelMode":"Driving","warnings":[{"origin":"22.29126,120.88053","severity":"Minor","text":"Minor Congestion","to":"22.291871,120.881179","warningType":"TrafficFlow"}]},{"compassDirection":"south","details":[{"compassDegrees":180,"endPathIndices":[420],"locationCodes":["D10+04896"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ba Shi Lane"],"roadType":"MajorRoad","startPathIndices":[417]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto Ba Shi Lane"},"maneuverPoint":{"type":"Point","coordinates":[22.206738,120.706519]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.122,"travelDuration":54,"travelMode":"Driving"},{"compassDirection":"southwest","details":[{"compassDegrees":225,"endPathIndices":[424],"locationCodes":["D10+04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Lane Ba Shih"],"roadType":"MajorRoad","startPathIndices":[420]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Lane Ba Shih"},"maneuverPoint":{"type":"Point","coordinates":[22.205778,120.705961]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":0.463,"travelDuration":26,"travelMode":"Driving"},{"compassDirection":"west","details":[{"compassDegrees":284,"endPathIndices":[434],"locationCodes":["D10+04896","D10P04896"],"maneuverType":"RoadNameChange","mode":"Driving","names":["Jiu Zhuang Rd."],"roadType":"MajorRoad","startPathIndices":[424]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"RoadNameChange","text":"Road name changes to Jiu Zhuang Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.204383,120.702292]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":1.561,"travelDuration":156,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":158,"endPathIndices":[521],"locationCodes":["D10P04822","D10+08959","D10+09021","D10+04823","D10+09011","D10+08960","D10+08961","D10+08962","D10+09006","D10+08963"],"maneuverType":"TurnLeft","mode":"Driving","names":["Ping E Highway"],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[434]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"TurnLeft","text":"Turn left onto 26 / Ping E Highway"},"maneuverPoint":{"type":"Point","coordinates":[22.194749,120.691981]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":21.793,"travelDuration":1594,"travelMode":"Driving"},{"compassDirection":"south","details":[{"compassDegrees":188,"endPathIndices":[628],"locationCodes":["D10+08964","D10+08965","D10+04824","D10+08966","D10P08966","D10+08967","D10P08967","D10+04825","D10+04826","D10+08968"],"maneuverType":"KeepToStayRight","mode":"Driving","names":["Heng Gong Rd."],"roadShieldRequestParameters":{"bucket":524956,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[521]}],"exit":"","iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"KeepToStayRight","text":"Keep right to stay on 26 / Heng Gong Rd."},"maneuverPoint":{"type":"Point","coordinates":[22.015738,120.744081]},"sideOfStreet":"Unknown","tollZone":"","transitTerminus":"","travelDistance":20.571,"travelDuration":1575,"travelMode":"Driving"},{"compassDirection":"north","details":[{"compassDegrees":356,"endPathIndices":[628],"locationCodes":["D10+08968"],"maneuverType":"ArriveFinish","mode":"Driving","names":["Keng Nei Rd."],"roadShieldRequestParameters":{"bucket":524951,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"MajorRoad","startPathIndices":[628]}],"exit":"","hints":[{"hintType":"NextIntersection","text":"If you reach Lane 55, Keng Nei Rd., you've gone too far"}],"iconType":"Auto","instruction":{"formattedText":null,"maneuverType":"ArriveFinish","text":"Arrive at 26 / Keng Nei Rd. on the right"},"maneuverPoint":{"type":"Point","coordinates":[21.91126,120.855322]},"sideOfStreet":"Right","tollZone":"","transitTerminus":"","travelDistance":0,"travelDuration":0,"travelMode":"Driving"}]
             * routeRegion : WR
             * routeSubLegs : [{"endWaypoint":{"type":"Point","coordinates":[21.91126,120.855322],"description":"26 / Keng Nei Rd.","isVia":false,"locationIdentifier":"4|7|69|67|57|145|181|164|78|0|0|224|1|229|199|112|63|2|21.91126,120.855322","routePathIndex":628},"startWaypoint":{"type":"Point","coordinates":[22.254728,120.893362],"description":"26","isVia":false,"locationIdentifier":"4|7|69|67|57|49|60|161|78|0|0|224|1|159|199|2|63|2|22.254728,120.893362","routePathIndex":0},"travelDistance":78.948,"travelDuration":6663}]
             * travelDistance : 78.948
             * travelDuration : 6663
             */

            private List<RouteLegsEntity> routeLegs;

            public String get__type() {
                return __type;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDistanceUnit() {
                return distanceUnit;
            }

            public void setDistanceUnit(String distanceUnit) {
                this.distanceUnit = distanceUnit;
            }

            public String getDurationUnit() {
                return durationUnit;
            }

            public void setDurationUnit(String durationUnit) {
                this.durationUnit = durationUnit;
            }

            public String getTrafficCongestion() {
                return trafficCongestion;
            }

            public void setTrafficCongestion(String trafficCongestion) {
                this.trafficCongestion = trafficCongestion;
            }

            public String getTrafficDataUsed() {
                return trafficDataUsed;
            }

            public void setTrafficDataUsed(String trafficDataUsed) {
                this.trafficDataUsed = trafficDataUsed;
            }

            public double getTravelDistance() {
                return travelDistance;
            }

            public void setTravelDistance(double travelDistance) {
                this.travelDistance = travelDistance;
            }

            public int getTravelDuration() {
                return travelDuration;
            }

            public void setTravelDuration(int travelDuration) {
                this.travelDuration = travelDuration;
            }

            public int getTravelDurationTraffic() {
                return travelDurationTraffic;
            }

            public void setTravelDurationTraffic(int travelDurationTraffic) {
                this.travelDurationTraffic = travelDurationTraffic;
            }

            public List<Double> getBbox() {
                return bbox;
            }

            public void setBbox(List<Double> bbox) {
                this.bbox = bbox;
            }

            public List<RouteLegsEntity> getRouteLegs() {
                return routeLegs;
            }

            public void setRouteLegs(List<RouteLegsEntity> routeLegs) {
                this.routeLegs = routeLegs;
            }

            public static class RouteLegsEntity {
                /**
                 * type : Point
                 * coordinates : [21.91126,120.855322]
                 */

                private ActualEndEntity actualEnd;
                /**
                 * type : Point
                 * coordinates : [22.254728,120.893362]
                 */

                private ActualStartEntity actualStart;
                private int cost;
                private String description;
                private String routeRegion;
                private double travelDistance;
                private int travelDuration;
                private List<?> alternateVias;
                /**
                 * compassDirection : north
                 * details : [{"compassDegrees":344,"endPathIndices":[13],"locationCodes":["D10+04829"],"maneuverType":"DepartStart","mode":"Driving","names":["26"],"roadShieldRequestParameters":{"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]},"roadType":"Street","startPathIndices":[0]}]
                 * exit :
                 * iconType : Auto
                 * instruction : {"formattedText":null,"maneuverType":"DepartStart","text":"Depart 26 toward Nan Tian Rd."}
                 * maneuverPoint : {"type":"Point","coordinates":[22.254728,120.893362]}
                 * sideOfStreet : Unknown
                 * tollZone :
                 * towardsRoadName : Nan Tian Rd.
                 * transitTerminus :
                 * travelDistance : 1.925
                 * travelDuration : 209
                 * travelMode : Driving
                 */

                private List<ItineraryItemsEntity> itineraryItems;
                /**
                 * endWaypoint : {"type":"Point","coordinates":[21.91126,120.855322],"description":"26 / Keng Nei Rd.","isVia":false,"locationIdentifier":"4|7|69|67|57|145|181|164|78|0|0|224|1|229|199|112|63|2|21.91126,120.855322","routePathIndex":628}
                 * startWaypoint : {"type":"Point","coordinates":[22.254728,120.893362],"description":"26","isVia":false,"locationIdentifier":"4|7|69|67|57|49|60|161|78|0|0|224|1|159|199|2|63|2|22.254728,120.893362","routePathIndex":0}
                 * travelDistance : 78.948
                 * travelDuration : 6663
                 */

                private List<RouteSubLegsEntity> routeSubLegs;

                public ActualEndEntity getActualEnd() {
                    return actualEnd;
                }

                public void setActualEnd(ActualEndEntity actualEnd) {
                    this.actualEnd = actualEnd;
                }

                public ActualStartEntity getActualStart() {
                    return actualStart;
                }

                public void setActualStart(ActualStartEntity actualStart) {
                    this.actualStart = actualStart;
                }

                public int getCost() {
                    return cost;
                }

                public void setCost(int cost) {
                    this.cost = cost;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getRouteRegion() {
                    return routeRegion;
                }

                public void setRouteRegion(String routeRegion) {
                    this.routeRegion = routeRegion;
                }

                public double getTravelDistance() {
                    return travelDistance;
                }

                public void setTravelDistance(double travelDistance) {
                    this.travelDistance = travelDistance;
                }

                public int getTravelDuration() {
                    return travelDuration;
                }

                public void setTravelDuration(int travelDuration) {
                    this.travelDuration = travelDuration;
                }

                public List<?> getAlternateVias() {
                    return alternateVias;
                }

                public void setAlternateVias(List<?> alternateVias) {
                    this.alternateVias = alternateVias;
                }

                public List<ItineraryItemsEntity> getItineraryItems() {
                    return itineraryItems;
                }

                public void setItineraryItems(List<ItineraryItemsEntity> itineraryItems) {
                    this.itineraryItems = itineraryItems;
                }

                public List<RouteSubLegsEntity> getRouteSubLegs() {
                    return routeSubLegs;
                }

                public void setRouteSubLegs(List<RouteSubLegsEntity> routeSubLegs) {
                    this.routeSubLegs = routeSubLegs;
                }

                public static class ActualEndEntity {
                    private String type;
                    private List<Double> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Double> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Double> coordinates) {
                        this.coordinates = coordinates;
                    }
                }

                public static class ActualStartEntity {
                    private String type;
                    private List<Double> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Double> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Double> coordinates) {
                        this.coordinates = coordinates;
                    }
                }

                public static class ItineraryItemsEntity {
                    private String compassDirection;
                    private String exit;
                    private String iconType;
                    /**
                     * formattedText : null
                     * maneuverType : DepartStart
                     * text : Depart 26 toward Nan Tian Rd.
                     */

                    private InstructionEntity instruction;
                    /**
                     * type : Point
                     * coordinates : [22.254728,120.893362]
                     */

                    private ManeuverPointEntity maneuverPoint;
                    private String sideOfStreet;
                    private String tollZone;
                    private String towardsRoadName;
                    private String transitTerminus;
                    private double travelDistance;
                    private int travelDuration;
                    private String travelMode;
                    /**
                     * compassDegrees : 344
                     * endPathIndices : [13]
                     * locationCodes : ["D10+04829"]
                     * maneuverType : DepartStart
                     * mode : Driving
                     * names : ["26"]
                     * roadShieldRequestParameters : {"bucket":524968,"shields":[{"labels":["26"],"roadShieldType":3}]}
                     * roadType : Street
                     * startPathIndices : [0]
                     */

                    private List<DetailsEntity> details;

                    public String getCompassDirection() {
                        return compassDirection;
                    }

                    public void setCompassDirection(String compassDirection) {
                        this.compassDirection = compassDirection;
                    }

                    public String getExit() {
                        return exit;
                    }

                    public void setExit(String exit) {
                        this.exit = exit;
                    }

                    public String getIconType() {
                        return iconType;
                    }

                    public void setIconType(String iconType) {
                        this.iconType = iconType;
                    }

                    public InstructionEntity getInstruction() {
                        return instruction;
                    }

                    public void setInstruction(InstructionEntity instruction) {
                        this.instruction = instruction;
                    }

                    public ManeuverPointEntity getManeuverPoint() {
                        return maneuverPoint;
                    }

                    public void setManeuverPoint(ManeuverPointEntity maneuverPoint) {
                        this.maneuverPoint = maneuverPoint;
                    }

                    public String getSideOfStreet() {
                        return sideOfStreet;
                    }

                    public void setSideOfStreet(String sideOfStreet) {
                        this.sideOfStreet = sideOfStreet;
                    }

                    public String getTollZone() {
                        return tollZone;
                    }

                    public void setTollZone(String tollZone) {
                        this.tollZone = tollZone;
                    }

                    public String getTowardsRoadName() {
                        return towardsRoadName;
                    }

                    public void setTowardsRoadName(String towardsRoadName) {
                        this.towardsRoadName = towardsRoadName;
                    }

                    public String getTransitTerminus() {
                        return transitTerminus;
                    }

                    public void setTransitTerminus(String transitTerminus) {
                        this.transitTerminus = transitTerminus;
                    }

                    public double getTravelDistance() {
                        return travelDistance;
                    }

                    public void setTravelDistance(double travelDistance) {
                        this.travelDistance = travelDistance;
                    }

                    public int getTravelDuration() {
                        return travelDuration;
                    }

                    public void setTravelDuration(int travelDuration) {
                        this.travelDuration = travelDuration;
                    }

                    public String getTravelMode() {
                        return travelMode;
                    }

                    public void setTravelMode(String travelMode) {
                        this.travelMode = travelMode;
                    }

                    public List<DetailsEntity> getDetails() {
                        return details;
                    }

                    public void setDetails(List<DetailsEntity> details) {
                        this.details = details;
                    }

                    public static class InstructionEntity {
                        private Object formattedText;
                        private String maneuverType;
                        private String text;

                        public Object getFormattedText() {
                            return formattedText;
                        }

                        public void setFormattedText(Object formattedText) {
                            this.formattedText = formattedText;
                        }

                        public String getManeuverType() {
                            return maneuverType;
                        }

                        public void setManeuverType(String maneuverType) {
                            this.maneuverType = maneuverType;
                        }

                        public String getText() {
                            return text;
                        }

                        public void setText(String text) {
                            this.text = text;
                        }
                    }

                    public static class ManeuverPointEntity {
                        private String type;
                        private List<Double> coordinates;

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public List<Double> getCoordinates() {
                            return coordinates;
                        }

                        public void setCoordinates(List<Double> coordinates) {
                            this.coordinates = coordinates;
                        }
                    }

                    public static class DetailsEntity {
                        private int compassDegrees;
                        private String maneuverType;
                        private String mode;
                        /**
                         * bucket : 524968
                         * shields : [{"labels":["26"],"roadShieldType":3}]
                         */

                        private RoadShieldRequestParametersEntity roadShieldRequestParameters;
                        private String roadType;
                        private List<Integer> endPathIndices;
                        private List<String> locationCodes;
                        private List<String> names;
                        private List<Integer> startPathIndices;

                        public int getCompassDegrees() {
                            return compassDegrees;
                        }

                        public void setCompassDegrees(int compassDegrees) {
                            this.compassDegrees = compassDegrees;
                        }

                        public String getManeuverType() {
                            return maneuverType;
                        }

                        public void setManeuverType(String maneuverType) {
                            this.maneuverType = maneuverType;
                        }

                        public String getMode() {
                            return mode;
                        }

                        public void setMode(String mode) {
                            this.mode = mode;
                        }

                        public RoadShieldRequestParametersEntity getRoadShieldRequestParameters() {
                            return roadShieldRequestParameters;
                        }

                        public void setRoadShieldRequestParameters(RoadShieldRequestParametersEntity roadShieldRequestParameters) {
                            this.roadShieldRequestParameters = roadShieldRequestParameters;
                        }

                        public String getRoadType() {
                            return roadType;
                        }

                        public void setRoadType(String roadType) {
                            this.roadType = roadType;
                        }

                        public List<Integer> getEndPathIndices() {
                            return endPathIndices;
                        }

                        public void setEndPathIndices(List<Integer> endPathIndices) {
                            this.endPathIndices = endPathIndices;
                        }

                        public List<String> getLocationCodes() {
                            return locationCodes;
                        }

                        public void setLocationCodes(List<String> locationCodes) {
                            this.locationCodes = locationCodes;
                        }

                        public List<String> getNames() {
                            return names;
                        }

                        public void setNames(List<String> names) {
                            this.names = names;
                        }

                        public List<Integer> getStartPathIndices() {
                            return startPathIndices;
                        }

                        public void setStartPathIndices(List<Integer> startPathIndices) {
                            this.startPathIndices = startPathIndices;
                        }

                        public static class RoadShieldRequestParametersEntity {
                            private int bucket;
                            /**
                             * labels : ["26"]
                             * roadShieldType : 3
                             */

                            private List<ShieldsEntity> shields;

                            public int getBucket() {
                                return bucket;
                            }

                            public void setBucket(int bucket) {
                                this.bucket = bucket;
                            }

                            public List<ShieldsEntity> getShields() {
                                return shields;
                            }

                            public void setShields(List<ShieldsEntity> shields) {
                                this.shields = shields;
                            }

                            public static class ShieldsEntity {
                                private int roadShieldType;
                                private List<String> labels;

                                public int getRoadShieldType() {
                                    return roadShieldType;
                                }

                                public void setRoadShieldType(int roadShieldType) {
                                    this.roadShieldType = roadShieldType;
                                }

                                public List<String> getLabels() {
                                    return labels;
                                }

                                public void setLabels(List<String> labels) {
                                    this.labels = labels;
                                }
                            }
                        }
                    }
                }

                public static class RouteSubLegsEntity {
                    /**
                     * type : Point
                     * coordinates : [21.91126,120.855322]
                     * description : 26 / Keng Nei Rd.
                     * isVia : false
                     * locationIdentifier : 4|7|69|67|57|145|181|164|78|0|0|224|1|229|199|112|63|2|21.91126,120.855322
                     * routePathIndex : 628
                     */

                    private EndWaypointEntity endWaypoint;
                    /**
                     * type : Point
                     * coordinates : [22.254728,120.893362]
                     * description : 26
                     * isVia : false
                     * locationIdentifier : 4|7|69|67|57|49|60|161|78|0|0|224|1|159|199|2|63|2|22.254728,120.893362
                     * routePathIndex : 0
                     */

                    private StartWaypointEntity startWaypoint;
                    private double travelDistance;
                    private int travelDuration;

                    public EndWaypointEntity getEndWaypoint() {
                        return endWaypoint;
                    }

                    public void setEndWaypoint(EndWaypointEntity endWaypoint) {
                        this.endWaypoint = endWaypoint;
                    }

                    public StartWaypointEntity getStartWaypoint() {
                        return startWaypoint;
                    }

                    public void setStartWaypoint(StartWaypointEntity startWaypoint) {
                        this.startWaypoint = startWaypoint;
                    }

                    public double getTravelDistance() {
                        return travelDistance;
                    }

                    public void setTravelDistance(double travelDistance) {
                        this.travelDistance = travelDistance;
                    }

                    public int getTravelDuration() {
                        return travelDuration;
                    }

                    public void setTravelDuration(int travelDuration) {
                        this.travelDuration = travelDuration;
                    }

                    public static class EndWaypointEntity {
                        private String type;
                        private String description;
                        private boolean isVia;
                        private String locationIdentifier;
                        private int routePathIndex;
                        private List<Double> coordinates;

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public boolean isIsVia() {
                            return isVia;
                        }

                        public void setIsVia(boolean isVia) {
                            this.isVia = isVia;
                        }

                        public String getLocationIdentifier() {
                            return locationIdentifier;
                        }

                        public void setLocationIdentifier(String locationIdentifier) {
                            this.locationIdentifier = locationIdentifier;
                        }

                        public int getRoutePathIndex() {
                            return routePathIndex;
                        }

                        public void setRoutePathIndex(int routePathIndex) {
                            this.routePathIndex = routePathIndex;
                        }

                        public List<Double> getCoordinates() {
                            return coordinates;
                        }

                        public void setCoordinates(List<Double> coordinates) {
                            this.coordinates = coordinates;
                        }
                    }

                    public static class StartWaypointEntity {
                        private String type;
                        private String description;
                        private boolean isVia;
                        private String locationIdentifier;
                        private int routePathIndex;
                        private List<Double> coordinates;

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public boolean isIsVia() {
                            return isVia;
                        }

                        public void setIsVia(boolean isVia) {
                            this.isVia = isVia;
                        }

                        public String getLocationIdentifier() {
                            return locationIdentifier;
                        }

                        public void setLocationIdentifier(String locationIdentifier) {
                            this.locationIdentifier = locationIdentifier;
                        }

                        public int getRoutePathIndex() {
                            return routePathIndex;
                        }

                        public void setRoutePathIndex(int routePathIndex) {
                            this.routePathIndex = routePathIndex;
                        }

                        public List<Double> getCoordinates() {
                            return coordinates;
                        }

                        public void setCoordinates(List<Double> coordinates) {
                            this.coordinates = coordinates;
                        }
                    }
                }
            }
        }
    }
}
