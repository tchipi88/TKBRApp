(function() {
    'use strict';

    angular
        .module('app')
        .controller('AccessGroupDialogController', AccessGroupDialogController);

    AccessGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'AccessGroup','Access'];

    function AccessGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, AccessGroup,Access ) {
        var vm = this;

        vm.accessGroup = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.accesss=Access.query();
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.accessGroup.id !== null) {
                AccessGroup.update(vm.accessGroup, onSaveSuccess, onSaveError);
            } else {
                AccessGroup.save(vm.accessGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:accessGroupUpdate', result);
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
