(function() {
    'use strict';

    angular
        .module('app')
        .controller('FournisseurDialogController', FournisseurDialogController);

    FournisseurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Fournisseur','Employe'];

    function FournisseurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Fournisseur ,Employe) {
        var vm = this;

        vm.fournisseur = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.responsables = Employe.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fournisseur.id !== null) {
                Fournisseur.update(vm.fournisseur, onSaveSuccess, onSaveError);
            } else {
                Fournisseur.save(vm.fournisseur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:fournisseurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
