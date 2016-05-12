/**
 * Created by steven on 12/05/16.
 */


'use strict';
angular.module('vivaxDataManagerApp')
    .factory('ShareDataService', function () {

        var myList;

        var addList = function(newObj, index) {
            myList[0][index].push(newObj);
            console.log(myList);
        }

        var getList = function(){
            return myList;
        }

        var setList = function(newList){
            myList=newList;
        }

        return {
            addList: addList,
            getList: getList,
            setList: setList
        };
    });
