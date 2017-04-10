(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeFonctionDialogController', EmployeFonctionDialogController);

    EmployeFonctionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'EmployeFonction'];

    function EmployeFonctionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, EmployeFonction ) {
        var vm = this;

        vm.employeFonction = entity;
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
            if (vm.employeFonction.id !== null) {
                EmployeFonction.update(vm.employeFonction, onSaveSuccess, onSaveError);
            } else {
                EmployeFonction.save(vm.employeFonction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:employeFonctionUpdate', result);
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
