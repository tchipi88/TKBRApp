(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonReceptionDialogController', BonReceptionDialogController);

    BonReceptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'BonReception','Achat','Employe'];

    function BonReceptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, BonReception ,Achat,Employe) {
        var vm = this;

        vm.bonReception = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.achats = Achat.query();
vm.employes = Employe.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bonReception.id !== null) {
                BonReception.update(vm.bonReception, onSaveSuccess, onSaveError);
            } else {
                BonReception.save(vm.bonReception, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:bonReceptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateReception = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
