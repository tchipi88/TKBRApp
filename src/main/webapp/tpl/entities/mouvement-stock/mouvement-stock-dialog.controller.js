(function () {
    'use strict';

    angular
            .module('app')
            .controller('MouvementStockDialogController', MouvementStockDialogController);

    MouvementStockDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'MouvementStock', 'Entrepot', 'Produit'];

    function MouvementStockDialogController($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, MouvementStock, Entrepot, Produit) {
        var vm = this;

        vm.mouvementStock = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.entrepots = Entrepot.query();
        vm.entrepots = Entrepot.query();
        vm.produits = Produit.query();



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.mouvementStock.id !== null) {
                MouvementStock.update(vm.mouvementStock, onSaveSuccess, onSaveError);
            } else {
                MouvementStock.save(vm.mouvementStock, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:mouvementStockUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


        vm.datePickerOpenStatus.dateTransaction = false;


        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
