(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('EntrepotDetailController', EntrepotDetailController);

    EntrepotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrepot', 'Employe'];

    function EntrepotDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrepot, Employe) {
        var vm = this;

        vm.entrepot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:entrepotUpdate', function(event, result) {
            vm.entrepot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
