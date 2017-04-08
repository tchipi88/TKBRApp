(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitDialogController', ProduitDialogController);

    ProduitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Produit', 'ProduitCategorie', 'Unite'];

    function ProduitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Produit, ProduitCategorie, Unite) {
        var vm = this;

        vm.produit = entity;
        vm.clear = clear;
        vm.save = save;
        vm.produitcategories = ProduitCategorie.query();
        vm.unites = Unite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.produit.id !== null) {
                Produit.update(vm.produit, onSaveSuccess, onSaveError);
            } else {
                Produit.save(vm.produit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:produitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
