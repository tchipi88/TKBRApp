(function() {
    'use strict';

    angular
        .module('app')
        .controller('AchatDialogController', AchatDialogController);

    AchatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Achat','Fournisseur'];

    function AchatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Achat ,Fournisseur) {
        var vm = this;

        vm.achat = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.fournisseurs = Fournisseur.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.achat.id !== null) {
                Achat.update(vm.achat, onSaveSuccess, onSaveError);
            } else {
                Achat.save(vm.achat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:achatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateEmission = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
