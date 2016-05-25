/**
 * Created by steven on 12/05/16.
 * Service class created to encapsulate
 * the sharing of data between controllers
 */
'use strict';
angular.module('vivaxDataManagerApp')
    .factory('ShareDataService', function () {
        /**
         * This property will hold the object involved in the
         * call to any CRUD operation
         */
        var object;
        /**
         * This is a boolean property that will change it's status depending
         * on if the CRUD operation is being submitted to the database
         * or only in the local variables
         */
        var flag;
        /**
         * This property holds the current state of the DTO object that holds
         * all the information of each Publication
         */
        var publi;

        var getObject= function(){
            return object;
        }

        var setObject = function(paramObject){
            object=paramObject;
        }

        var getPubli= function(){
            return publi;
        }

        var setPubli = function(paramPubli){
            publi=paramPubli;
        }

        var getFlag = function(){
            return flag;
        }

        var setFlag = function(paramFlag){
            flag=paramFlag;
        }

        return {
            getObject: getObject,
            setObject: setObject,
            getPubli: getPubli,
            setPubli: setPubli,
            getFlag : getFlag,
            setFlag : setFlag
        };
    });
