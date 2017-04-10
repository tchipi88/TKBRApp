(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProduitCategorieDetailController', ProduitCategorieDetailController);

    ProduitCategorieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ProduitCategorie' ];

    function ProduitCategorieDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ProduitCategorie ) {
        var vm = this;

        vm.produitCategorie = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:produitCategorieUpdate', function(event, result) {
            vm.produitCategorie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
