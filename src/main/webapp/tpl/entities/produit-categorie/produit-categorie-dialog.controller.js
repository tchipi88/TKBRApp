(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitCategorieDialogController', ProduitCategorieDialogController);

    ProduitCategorieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProduitCategorie'];

    function ProduitCategorieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProduitCategorie) {
        var vm = this;

        vm.produitCategorie = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.produitCategorie.id !== null) {
                ProduitCategorie.update(vm.produitCategorie, onSaveSuccess, onSaveError);
            } else {
                ProduitCategorie.save(vm.produitCategorie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:produitCategorieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
