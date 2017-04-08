(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('PvReceptionDetailController', PvReceptionDetailController);

    PvReceptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'PvReception', 'Commande', 'Employe'];

    function PvReceptionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, PvReception, Commande, Employe) {
        var vm = this;

        vm.pvReception = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:pvReceptionUpdate', function(event, result) {
            vm.pvReception = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
