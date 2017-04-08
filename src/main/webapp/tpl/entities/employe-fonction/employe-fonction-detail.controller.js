(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('EmployeFonctionDetailController', EmployeFonctionDetailController);

    EmployeFonctionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmployeFonction'];

    function EmployeFonctionDetailController($scope, $rootScope, $stateParams, previousState, entity, EmployeFonction) {
        var vm = this;

        vm.employeFonction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:employeFonctionUpdate', function(event, result) {
            vm.employeFonction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
