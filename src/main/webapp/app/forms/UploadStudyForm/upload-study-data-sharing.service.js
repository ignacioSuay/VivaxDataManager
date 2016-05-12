/**
 * Created by steven on 12/05/16.
 */


'use strict';
angular.module('vivaxDataManagerApp')
    .factory('ShareDataService', function () {

        var myList = [];

        var addList = function(newObj) {
            myList.push(newObj);
            console.log(myList);
        }

        var getList = function(){
            return myList;
        }

        return {
            addList: addList,
            getList: getList
        };
    });
