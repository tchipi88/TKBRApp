(function () {
    'use strict';

    var jhiAlert = {
        template: '<div  class="m-b-md">' +
                ' <alert ng-repeat="alert in $ctrl.alerts" type="{{alert.type}}" close="alert.close($ctrl.alerts)">{{alert.msg}}</alert></div>'
        ,
        controller: jhiAlertController
    };

    angular
            .module('app')
            .component('jhiAlert', jhiAlert);

    jhiAlertController.$inject = ['$scope', 'AlertService'];

    function jhiAlertController($scope, AlertService) {
        var vm = this;

        vm.alerts = AlertService.get();
        $scope.$on('$destroy', function () {
            vm.alerts = [];
        });
    }
})();


