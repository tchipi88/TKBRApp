(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueFournisseurDetailController', CompteAnalytiqueFournisseurDetailController);

    CompteAnalytiqueFournisseurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CompteAnalytiqueFournisseur' ,'Fournisseur'];

    function CompteAnalytiqueFournisseurDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CompteAnalytiqueFournisseur ,Fournisseur) {
        var vm = this;

        vm.compteAnalytiqueFournisseur = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:compteAnalytiqueFournisseurUpdate', function(event, result) {
            vm.compteAnalytiqueFournisseur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
