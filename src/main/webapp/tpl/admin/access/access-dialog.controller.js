(function() {
    'use strict';

    angular
        .module('app')
        .controller('AccessDialogController', AccessDialogController);

    AccessDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Access'];

    function AccessDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Access,AccessGroup ) {
        var vm = this;

        vm.access = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.accessGroups=AccessGroup.query();
        
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.access.id !== null) {
                Access.update(vm.access, onSaveSuccess, onSaveError);
            } else {
                Access.save(vm.access, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:accessUpdate', result);
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
