/**
 * Created by steven on 12/05/16.
 */


'use strict';
angular.module('vivaxDataManagerApp')
    .factory('ShareDataService', function () {

        var siteData;
        var flag;

        //TODO change names of variable and methods to make the service universal
        var getSiteData= function(){
            return siteData;
        }

        var setSiteData = function(paramSiteData){
            siteData=paramSiteData;
        }

        var getFlag = function(){
            return flag;
        }

        var setFlag = function(paramFlag){
            flag=paramFlag;
        }

        return {
            getSiteData: getSiteData,
            setSiteData: setSiteData,
            getFlag : getFlag,
            setFlag : setFlag
        };
    });
