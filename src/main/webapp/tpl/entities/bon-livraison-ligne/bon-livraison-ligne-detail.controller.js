(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonLivraisonLigneDetailController', BonLivraisonLigneDetailController);

    BonLivraisonLigneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'BonLivraisonLigne' ,'BonLivraison','CommandeLigne'];

    function BonLivraisonLigneDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, BonLivraisonLigne ,BonLivraison,CommandeLigne) {
        var vm = this;

        vm.bonLivraisonLigne = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:bonLivraisonLigneUpdate', function(event, result) {
            vm.bonLivraisonLigne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
