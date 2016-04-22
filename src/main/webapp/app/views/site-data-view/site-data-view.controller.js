(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataViewController', SiteDataViewController);
    
    SiteDataViewController.$inject = ['$scope', '$state', 'SiteDataViewDTO', 'SiteDataSearch'];
    console.log('in controller function')
    function SiteDataViewController ($scope, $state, SiteDataViewDTO, SiteDataSearch) {
   	
        
        $scope.siteDataViewDTOS = [];
        $scope.loadAll = function() {
            var data = [{name:'country', query:'China'}];
            $scope.siteDataViewDTOS = SiteDataViewDTO.query(data);
             $scope.authors=$scope.siteDataViewDTOS;
            console.log($scope.authors);
        };

        $scope.search = function () {
            if (!$scope.searchQuery) {
                return $scope.loadAll();
            }
            SiteDataSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.siteDataViewDTOS = result;
            });
        };
        $scope.loadAll();
     }
})();