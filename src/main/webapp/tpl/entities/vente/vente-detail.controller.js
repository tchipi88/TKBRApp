(function() {
    'use strict';

    angular
        .module('app')
        .controller('VenteDetailController', VenteDetailController);

    VenteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Vente' ,'Client'];

    function VenteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Vente ,Client) {
        var vm = this;

        vm.vente = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:venteUpdate', function(event, result) {
            vm.vente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
