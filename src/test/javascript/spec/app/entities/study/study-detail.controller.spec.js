'use strict';

describe('Controller Tests', function() {

    describe('Study Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudy, MockPublication, MockSiteData;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudy = jasmine.createSpy('MockStudy');
            MockPublication = jasmine.createSpy('MockPublication');
            MockSiteData = jasmine.createSpy('MockSiteData');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Study': MockStudy,
                'Publication': MockPublication,
                'SiteData': MockSiteData
            };
            createController = function() {
                $injector.get('$controller')("StudyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vivaxDataManagerApp:studyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
