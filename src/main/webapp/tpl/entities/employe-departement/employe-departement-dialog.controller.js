(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeDepartementDialogController', EmployeDepartementDialogController);

    EmployeDepartementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'EmployeDepartement'];

    function EmployeDepartementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, EmployeDepartement ) {
        var vm = this;

        vm.employeDepartement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employeDepartement.id !== null) {
                EmployeDepartement.update(vm.employeDepartement, onSaveSuccess, onSaveError);
            } else {
                EmployeDepartement.save(vm.employeDepartement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:employeDepartementUpdate', result);
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
