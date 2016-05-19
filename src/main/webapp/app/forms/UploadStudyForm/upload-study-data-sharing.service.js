/**
 * Created by steven on 12/05/16.
 */
'use strict';
angular.module('vivaxDataManagerApp')
    .factory('ShareDataService', function () {

        var object;
        var flag;

        //TODO change names of variable and methods to make the service universal
        var getObject= function(){
            return object;
        }

        var setObject = function(paramObject){
            object=paramObject;
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
            getFlag : getFlag,
            setFlag : setFlag
        };
    });
